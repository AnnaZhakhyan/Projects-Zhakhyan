extends Camera2D

func reset():
	create_tween().tween_property(self,"position",Vector2(353.0,750.0),0.3).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	create_tween().tween_property(self,"zoom",Vector2(1,1),0.3).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)

func focus(chara : Control):
	if chara is CharaPlayer:
		create_tween().tween_property(self,"position",Vector2(251,chara.global_position.y),0.4).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	else:
		chara = chara.get_parent()
		create_tween().tween_property(self,"position",Vector2(455,chara.global_position.y),0.4).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	create_tween().tween_property(self,"zoom",Vector2(2.255,2.255),0.4).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)

func focus_all_enemies():
	create_tween().tween_property(self,"position",Vector2(477.0,750.0),0.4).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	create_tween().tween_property(self,"zoom",Vector2(1.55,1.55),0.4).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
	pass
	
