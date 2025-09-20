extends Control
class_name EffectLabel

func setup(effectName : String, effectValue : int, turnsLeft : int, posY : int):
	$HBOX/PanelContainer/MarginContainer/EffectName.text = effectName
	if turnsLeft == -2: # Stack
		$TurnCounter.visible = false
		$Arrow.visible = false
		$HBOX/PanelContainer2/MarginContainer/EffectValue.text = str(effectValue)
	else: # Status Effect
		
		if effectValue < 0: 	# Debuff Effect
			$Arrow.rotation_degrees = -194.7
			$Arrow.modulate = Color("ff8c8c")
			effectValue = abs(effectValue)
		
		if turnsLeft == -1: 	# Infinite Effect
			$TurnCounter.text = ""
			
		else: 					# Finite Effect
			$TurnCounter.text = str(turnsLeft) + " Turns Left"
		
		$HBOX/PanelContainer2/MarginContainer/EffectValue.text = str(effectValue)+"%"
		
	
	position = Vector2(-100, posY)
	create_tween().tween_property(self,"position:x",365.0,0.3).set_trans(Tween.TRANS_SINE)

func leave():
	create_tween().tween_property(self,"position:x",-100,0.3).set_trans(Tween.TRANS_SINE)
	await get_tree().create_timer(0.3).timeout
	queue_free()
