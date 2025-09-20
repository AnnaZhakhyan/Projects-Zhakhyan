extends ColorRect

var LoadedEffectLabel : PackedScene = load("res://scenes/battlegrounds/NDE_EffectLabel.tscn")
var EffectLabels : Array[EffectLabel]

func _ready() -> void:
	SignalBus.charaPreviewed.connect(charaPreviewed)
	SignalBus.abilityPreviewed.connect(abilityPreviewed)

func setup(chara : Chara, cardQuote : String = ""):
	
	$"NAME&TITLE/NAME".text = chara.NAME
	$"NAME&TITLE/TITLE".text = chara.TITLE
	
	$"STATS/ATK#".text = str(chara.ATK)
	$"STATS/SAK#".text = str(chara.S_ATK)
	$"STATS/DEF#".text = str(chara.DEF)
	$"STATS/SDF#".text = str(chara.S_DEF)
	$"STATS/SPD#".text = str(chara.SPD)
	
	if cardQuote != "":
		$QUOTE.text = cardQuote
	
	match chara.COLOR:
		0: 
			$TYPE.text = "[AGL]"
			$TYPE.set("theme_override_colors/font_color",Color("4d79d6"))
		1: 
			$TYPE.text = "[STR]"
			$TYPE.set("theme_override_colors/font_color",Color("d6664d"))
		2: 
			$TYPE.text = "[PHY]"
			$TYPE.set("theme_override_colors/font_color",Color("d6a24d"))
		3: 
			$TYPE.text = "[INT]"
			$TYPE.set("theme_override_colors/font_color",Color("a44dd6"))
		4: 
			$TYPE.text = "[TEQ]"
			$TYPE.set("theme_override_colors/font_color",Color("22a12a"))
	
	$CharaSplash.texture = chara.SPLASH

func abilityPreviewed(chara : Chara):
	# If no CHARA given, Hide instead.
	if(chara == null):
		if visible :
			for effectLabel : EffectLabel in EffectLabels:
				effectLabel.leave()
			EffectLabels.clear()
			$AnimationPlayer.play("HIDE")
			await $AnimationPlayer.animation_finished
			visible = false
		return
	
	setup(chara)
	visible = true
	
	if chara is CharaPlayer: 
		$AnimationPlayer.play("SHOW")
		# X is 365.0
		var effectYPos : int = 472
		
		for child in chara.get_children():
			if child is Stack and child.value != 0:
				var effectLabel : EffectLabel = LoadedEffectLabel.instantiate()
				effectLabel.setup(child.stackName,child.value,-1,effectYPos)
				add_child(effectLabel)
				EffectLabels.append(effectLabel)
				effectYPos = effectYPos + 63
				await get_tree().create_timer(0.05).timeout
			elif child is StatusEffect:
				var effectLabel : EffectLabel = LoadedEffectLabel.instantiate()
				effectLabel.setup(child.statString,child.value,child.turnCountdown,effectYPos)
				add_child(effectLabel)
				EffectLabels.append(effectLabel)
				effectYPos = effectYPos + 63
				await get_tree().create_timer(0.05).timeout
		
	if chara is CharaEnemy: 
		$AnimationPlayer.play("SHOW_ENEMY")
	pass

func charaPreviewed(chara : Chara):
	# If no CHARA given, Hide instead.
	if(chara == null):
		if visible :
			for effectLabel : EffectLabel in EffectLabels:
				effectLabel.leave()
			EffectLabels.clear()
			$AnimationPlayer.play("HIDE_CHARA")
			await $AnimationPlayer.animation_finished
			visible = false
		return
	
	setup(chara)
	$CharaCard.setup(chara.charaRes)
	visible = true
	
	if chara is CharaPlayer: 
		$AnimationPlayer.play("SHOW_CHARA")
		# X is 365.0
		var effectYPos : int = 472
		
		for child in chara.get_children():
			
			# Showcase Stacks
			if child is Stack and child.value != 0:
				var effectLabel : EffectLabel = LoadedEffectLabel.instantiate()
				effectLabel.setup(child.stackName,child.value,-2,effectYPos)
				add_child(effectLabel)
				EffectLabels.append(effectLabel)
				effectYPos = effectYPos + 63
				await get_tree().create_timer(0.05).timeout
			
			# Showcase Status Effects
			elif child is StatusEffect:
				var effectLabel : EffectLabel = LoadedEffectLabel.instantiate()
				effectLabel.setup(child.statString,child.value,child.turnCountdown,effectYPos)
				add_child(effectLabel)
				EffectLabels.append(effectLabel)
				effectYPos = effectYPos + 63
				await get_tree().create_timer(0.05).timeout
		
	if chara is CharaEnemy: 
		$AnimationPlayer.play("SHOW_ENEMY")
	pass

func showcase_damage():
	visible = true
	$AnimationPlayer.play("DAMAGE")
	await $AnimationPlayer.animation_finished
	visible = false
