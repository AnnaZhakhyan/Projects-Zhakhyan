extends Control

func _ready():
	$AnimationPlayer.play("Dance")

func _on_button_button_down():
	get_tree().root.add_child(load("res://scenes/battlegrounds/NDE_Battlegrounds.tscn").instantiate())
	self.queue_free()


func _on_button_2_pressed() -> void:
	get_tree().change_scene_to_file("res://scenes/main-menu/MainScene.tscn")
	self.queue_free()
	pass # Replace with function body.
