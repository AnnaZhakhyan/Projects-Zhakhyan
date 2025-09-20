extends TextureRect
class_name AbilityCard

# Card Variables  --------------------
var cardOwner : String
var cardName : String
var cardPower : int
var cardColor : Utils.COLOR
var cardType : Utils.TYPE
var cardDesc : String
var cardQuote = "To Be Determined."
var cardTarget : Utils.TARGET = Utils.TARGET.SINGLE_ENEMY
var cardFonts = [80,50,37]

var hasCounter : bool = false

var charaOwner : Chara

# Misc Variables  --------------------
var og_pos : Vector2
var is_held : bool = false
var is_movable : bool = false
var previewing_card : bool = false
var is_releaseLabelVisible : bool = false
var locked : bool = false

# Setup Signals  ---------------------
func _ready() -> void:
	SignalBus.begin_turn.connect(start_of_turn_effect)
	SignalBus.enemy_card_played.connect(check_weekness)
	SignalBus.end_turn.connect(end_of_turn_effect)
	SignalBus.end_turn.connect(hide_weakness)
	SignalBus.enemy_damage_dealt.connect(with_damage_effect_condition)
	if !locked: gui_input.connect(input)

# Setup Card Textures & Labels  ------
func setup():
	# Setup The Labels 
	# Front
	$MRG/VBOX/L_OWNER.text = cardOwner
	$MRG/VBOX/L_NAME.text = cardName
	$MRG/VBOX/VBOX/L_POWER.text = str(cardPower)
	$MRG/VBOX/VBOX/L_TYPE.text = "Special"
	# Back
	$CARD_BACK/MRG/VBOX/L_OWNER.text = cardOwner
	$CARD_BACK/MRG/VBOX/L_NAME.text = cardName
	$CARD_BACK/MRG/VBOX/VBOX/L_POWER.text = str(cardPower)
	$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.text = "Special"
	$CARD_BACK/MRG/VBOX/L_DESC.text = cardDesc
	
	match cardType:
		Utils.TYPE.PHYSICAL: 
			$MRG/VBOX/VBOX/L_TYPE.text = "PHYSICAL"
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.text = "PHYSICAL"
		Utils.TYPE.SPECIAL: 
			$MRG/VBOX/VBOX/L_TYPE.text = "SPECIAL"
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.text = "SPECIAL"
	
	# Set The Font to Fit the Card
	$MRG/VBOX/L_NAME.set("theme_override_font_sizes/font_size",cardFonts[0])	
	$CARD_BACK/MRG/VBOX/L_NAME.set("theme_override_font_sizes/font_size",cardFonts[1])	
	$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_font_sizes/font_size",cardFonts[2])	
	
	# Change The Back Texture
	match cardColor:
		Utils.COLOR.AGL : 
			texture = load("res://art/cards/AC_AGL.png"); 
			$MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("b4befa"))
			$MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("b4befa"))
			$CARD_BACK/MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("b4befa"))
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("b4befa"))
			$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_colors/font_color",Color("b4befa"))
			$L_SIDE.set("theme_override_colors/font_color",Color("4d79d6"))
		Utils.COLOR.STR : 
			texture = load("res://art/cards/AC_STR.png");
			$MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("fbc5b4"))
			$MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("fbc5b4")) 
			$CARD_BACK/MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("fbc5b4"))
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("fbc5b4"))
			$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_colors/font_color",Color("fbc5b4"))
			$L_SIDE.set("theme_override_colors/font_color",Color("d6664d"))
		Utils.COLOR.PHY : 
			texture = load("res://art/cards/AC_PHY.png"); 
			$MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("faddb4"))
			$MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("faddb4"))
			$CARD_BACK/MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("faddb4"))
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("faddb4"))
			$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_colors/font_color",Color("faddb4"))
			$L_SIDE.set("theme_override_colors/font_color",Color("d6a24d"))
		Utils.COLOR.INT : 
			texture = load("res://art/cards/AC_INT.png"); 
			$MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("e1b4fa"))
			$MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("e1b4fa"))
			$CARD_BACK/MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("e1b4fa"))
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("e1b4fa"))
			$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_colors/font_color",Color("e1b4fa"))
			$L_SIDE.set("theme_override_colors/font_color",Color("a44dd6"))
		Utils.COLOR.TEQ : 
			texture = load("res://art/cards/AC_TEQ.png"); 
			$MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("ccfab4"))
			$MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("ccfab4"))
			$CARD_BACK/MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("ccfab4"))
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("ccfab4"))
			$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_colors/font_color",Color("ccfab4"))
			$L_SIDE.set("theme_override_colors/font_color",Color("7bd64d"))
	$CARD_BACK.texture = texture
	
func ability_activate(isCounter : bool = false):
	SignalBus.abilityActivated.emit(self, isCounter)
	$Shadow.modulate.a = 0;
	$ReleaseLabel.visible = false
	z_index = 0
	return
	
# Handle Input  ---------------------
func input(event: InputEvent) -> void:
	# If it's not touch or drag, stop function.
	if event is InputEventScreenTouch:
		if event.is_pressed():
			# Ability Pressed On
			$Timer.start()
			is_held = true
		
		elif event.is_released():
			# Ability Released On
			$Timer.stop()
			is_held = false
			if previewing_card: 
				close_preview()
				return
			
			# Activate Ability 
			if position.y < -350:
				ability_activate()
				return
			
			if is_movable: 
				# Reset Card to the Original Position
				create_tween().tween_property(self,"position",og_pos,0.2).set_trans(Tween.TRANS_SINE)
				create_tween().tween_property(self,"scale",Vector2(0.915,0.915),0.2)
				create_tween().tween_property($Shadow,"modulate:a",0,0.2)
				await get_tree().create_timer(0.2).timeout
				z_index = 0
			
	if event is InputEventScreenDrag and is_movable:
		# Move The Card Around
		$Timer.stop()
		z_index = 1
		
		# Move if not previewing.
		if !previewing_card: 
			position = position + event.relative
			
			# Show and Hide the Relese Label
			if position.y < -350 and !is_releaseLabelVisible:
				$ReleaseLabel.visible = true
				is_releaseLabelVisible = true
				create_tween().tween_property($ReleaseLabel,"modulate:a",1,0.2)
			elif position.y >= -350 and is_releaseLabelVisible:
				is_releaseLabelVisible = false
				create_tween().tween_property($ReleaseLabel,"modulate:a",0,0.2)
				await get_tree().create_timer(0.2).timeout
				$ReleaseLabel.visible = false
		
			# Scale if dragged up.
			var scl = 0.915 + (position.y - og_pos.y)/-3000
			scl = clamp(scl, 0.915, 1.15)
			scale = Vector2(scl,scl)
			
			# Set Opacity of the Shadow
			$Shadow.modulate.a = (scl - 0.915) / 0.235

# If the button is pressed and timer runs out, preview the card.
func _on_timer_timeout() -> void:
	if is_held : preview()

func preview():
	# Setup
	z_index = 1
	previewing_card = true
	
	#Move and Scale The Card
	create_tween().tween_property(self,"position",Vector2(132.59,-449.908),0.2).set_trans(Tween.TRANS_SINE)
	create_tween().tween_property(self,"scale",Vector2(1.35,1.35),0.2)
	await get_tree().create_timer(0.4).timeout
	
	#Close Preview if stopped holding mid-way through.
	if !is_held : close_preview(); return;
	
	#Flip The Card
	SignalBus.abilityPreviewed.emit(charaOwner)
	$AP.play("BACK"); await $AP.animation_finished

func close_preview():
	#Flip The Card and Hide Preview Screen
	$AP.play("FRONT")
	SignalBus.abilityPreviewed.emit(null)
	await $AP.animation_finished
	
	#Reset to The Original Position and Size
	create_tween().tween_property(self,"position",og_pos,0.2).set_trans(Tween.TRANS_SINE)
	match is_movable:
		true: create_tween().tween_property(self,"scale",Vector2(0.915,0.915),0.2)
		false: create_tween().tween_property(self,"scale",Vector2(0.64,0.64),0.2)
	await get_tree().create_timer(0.2).timeout
	
	#Clean-Up
	previewing_card = false
	z_index = 0

func power_up():
	$PowerEffect.visible = true
	pass
	
func power_down():
	$PowerEffect.visible = false
	pass

func with_damage_effect_condition(a_card : AbilityCard, final_dmg : int):
	if a_card == self: with_damage_effect(final_dmg)

func check_weekness(enemy_card : TextureRect):
	if Utils.color_modifier(enemy_card.cardColor,cardColor) == 1.35:
		var opos : Vector2 = $WeakLabel.position
		$WeakLabel.position.y += 100
		$WeakLabel.visible = true
		create_tween().tween_property($WeakLabel,"position",opos,0.5)
	pass

func hide_weakness():
	$WeakLabel.visible = false

func enter_hand_effect(): pass
func before_ability_effect(): pass
func with_damage_effect(_final_damage): pass
func after_ability_effect(): pass
func start_of_turn_effect(_isPlayerTurn : bool): pass
func end_of_turn_effect(): pass
func before_defending_effect(_enemyAbilityCard : EnemyCard): pass
func after_defending_effect(_enemyAbilityCard : EnemyCard): 
	if hasCounter: ability_activate(true)
