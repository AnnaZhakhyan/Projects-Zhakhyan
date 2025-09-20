extends VBoxContainer

var og_pos

func _ready():
	og_pos = global_position
	modulate.a = 0
	
func appear():
	create_tween().tween_property(self,"modulate:a",1,0.5).set_trans(Tween.TRANS_CUBIC)
	pass
	
func dissapear():
	create_tween().tween_property(self,"modulate:a",0,0.5).set_trans(Tween.TRANS_CUBIC)
	pass
