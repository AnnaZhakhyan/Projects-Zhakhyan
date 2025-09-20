extends TextureRect
class_name EnemyCard

var cardOwner : CharaEnemy
var cardQuote : String
var cardPower : int
var cardColor : Utils.COLOR
var cardName : String
var cardType : Utils.TYPE

var is_held : bool = false

func setup(enemyCardRes : EnemyCardRes, charaOwner : CharaEnemy):
	
	cardOwner = charaOwner
	cardQuote = enemyCardRes.cardQuote
	cardPower = enemyCardRes.cardPower
	cardName = enemyCardRes.cardName
	cardType = enemyCardRes.cardType
	cardColor = enemyCardRes.cardColor
	
	# Setup The Labels 
	# Front
	$MRG/VBOX/L_OWNER.text = enemyCardRes.cardOwner
	$CARD_BACK/MRG/VBOX/L_OWNER.text = enemyCardRes.cardOwner
	$MRG/VBOX/L_NAME.text = enemyCardRes.cardName
	$CARD_BACK/MRG/VBOX/L_NAME.text = enemyCardRes.cardName
	$MRG/VBOX/VBOX/L_POWER.text = str(enemyCardRes.cardPower)
	$CARD_BACK/MRG/VBOX/VBOX/L_POWER.text = str(enemyCardRes.cardPower)
	
	match cardType:
		Utils.TYPE.PHYSICAL: 
			$MRG/VBOX/VBOX/L_TYPE.text = "PHYSICAL"
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.text = "PHYSICAL"
		Utils.TYPE.SPECIAL: 
			$MRG/VBOX/VBOX/L_TYPE.text = "SPECIAL"
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.text = "SPECIAL"

	# Lower The Font to Fit the Card
	while($MRG/VBOX/L_NAME.size.y > 145): # Front
		var font_size = $MRG/VBOX/L_NAME.get("theme_override_font_sizes/font_size")
		$MRG/VBOX/L_NAME.set("theme_override_font_sizes/font_size",font_size-1)	
	
	match enemyCardRes.cardColor:
		Utils.COLOR.AGL : 
			texture = load("res://art/cards/EAC_AGL.png"); 
			$MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("b4befa"))
			$CARD_BACK/MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("b4befa"))
			$MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("b4befa"))
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("b4befa"))
			$MRG/VBOX/L_NAME.set("theme_override_colors/font_color",Color("b4befa"))
			$CARD_BACK/MRG/VBOX/L_NAME.set("theme_override_colors/font_color",Color("b4befa"))
			$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_colors/font_color",Color("b4befa"))
			$MRG/VBOX/VBOX/L_POWER.set("theme_override_colors/font_color",Color("b4befa"))
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("b4befa"))
			$L_SIDE.set("theme_override_colors/font_color",Color("4d79d6"))
		Utils.COLOR.STR : 
			texture = load("res://art/cards/EAC_STR.png");
			$MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("fbc5b4"))
			$CARD_BACK/MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("fbc5b4"))
			$MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("fbc5b4")) 
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("fbc5b4")) 
			$MRG/VBOX/L_NAME.set("theme_override_colors/font_color",Color("fbc5b4"))
			$CARD_BACK/MRG/VBOX/L_NAME.set("theme_override_colors/font_color",Color("fbc5b4"))
			$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_colors/font_color",Color("fbc5b4"))
			$MRG/VBOX/VBOX/L_POWER.set("theme_override_colors/font_color",Color("fbc5b4"))
			$CARD_BACK/MRG/VBOX/VBOX/L_POWER.set("theme_override_colors/font_color",Color("fbc5b4"))
			$L_SIDE.set("theme_override_colors/font_color",Color("d6664d"))
		Utils.COLOR.PHY : 
			texture = load("res://art/cards/EAC_PHY.png"); 
			$MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("faddb4"))
			$CARD_BACK/MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("faddb4"))
			$MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("faddb4"))
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("faddb4"))
			$MRG/VBOX/L_NAME.set("theme_override_colors/font_color",Color("faddb4"))
			$CARD_BACK/MRG/VBOX/L_NAME.set("theme_override_colors/font_color",Color("faddb4"))
			$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_colors/font_color",Color("faddb4"))
			$MRG/VBOX/VBOX/L_POWER.set("theme_override_colors/font_color",Color("faddb4"))
			$CARD_BACK/MRG/VBOX/VBOX/L_POWER.set("theme_override_colors/font_color",Color("faddb4"))
			$L_SIDE.set("theme_override_colors/font_color",Color("d6a24d"))
		Utils.COLOR.INT : 
			texture = load("res://art/cards/EAC_int.png"); 
			$MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("e1b4fa"))
			$CARD_BACK/MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("e1b4fa"))
			$MRG/VBOX/VBOX/L_POWER.set("theme_override_colors/font_color",Color("e1b4fa"))
			$CARD_BACK/MRG/VBOX/VBOX/L_POWER.set("theme_override_colors/font_color",Color("e1b4fa"))
			$MRG/VBOX/L_NAME.set("theme_override_colors/font_color",Color("e1b4fa"))
			$CARD_BACK/MRG/VBOX/L_NAME.set("theme_override_colors/font_color",Color("e1b4fa"))
			$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_colors/font_color",Color("e1b4fa"))
			$MRG/VBOX/VBOX/L_POWER.set("theme_override_colors/font_color",Color("e1b4fa"))
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("e1b4fa"))
			$L_SIDE.set("theme_override_colors/font_color",Color("a44dd6"))
		Utils.COLOR.TEQ : 
			texture = load("res://art/cards/EAC_TEQ.png"); 
			$MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("ccfab4"))
			$CARD_BACK/MRG/VBOX/L_OWNER.set("theme_override_colors/font_color",Color("ccfab4"))
			$MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("ccfab4"))
			$CARD_BACK/MRG/VBOX/VBOX/L_TYPE.set("theme_override_colors/font_color",Color("ccfab4"))
			$MRG/VBOX/L_NAME.set("theme_override_colors/font_color",Color("ccfab4"))
			$CARD_BACK/MRG/VBOX/L_NAME.set("theme_override_colors/font_color",Color("ccfab4"))
			$CARD_BACK/MRG/VBOX/L_DESC.set("theme_override_colors/font_color",Color("ccfab4"))
			$MRG/VBOX/VBOX/L_POWER.set("theme_override_colors/font_color",Color("ccfab4"))
			$CARD_BACK/MRG/VBOX/VBOX/L_POWER.set("theme_override_colors/font_color",Color("ccfab4"))
			$L_SIDE.set("theme_override_colors/font_color",Color("7bd64d"))
	$CARD_BACK.texture = texture
	
	
func _on_gui_input(event: InputEvent) -> void:
	if event is InputEventScreenTouch and event.is_pressed():
		is_held = true
		$Timer.start()
		
	if event is InputEventScreenTouch and event.is_released():
		is_held = false
		$Timer.stop()
		if $CARD_BACK.visible or $AP.is_playing():
			$AP.play("FLIP_BACK")
			SignalBus.abilityPreviewed.emit(null)
			await $AP.animation_finished
			z_index = 0
			create_tween().tween_property(self,"scale",Vector2(0.915,0.915),0.2)	  
		
func _on_timer_timeout() -> void:
	if is_held:
		print("[EnemyCard] Previewing " + cardName)
		z_index = 1
		create_tween().tween_property(self,"scale",Vector2(1.35,1.35),0.2)
		await get_tree().create_timer(0.4).timeout
		if !is_held: 
			create_tween().tween_property(self,"scale",Vector2(0.915,0.915),0.2)
			return
		$AP.play("FLIP")
		SignalBus.abilityPreviewed.emit(cardOwner)
	pass # Replace with functi on body.
