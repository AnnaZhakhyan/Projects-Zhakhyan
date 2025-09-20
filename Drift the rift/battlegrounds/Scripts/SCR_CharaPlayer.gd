extends Chara
class_name CharaPlayer

var timer : Timer

func _ready() -> void:
	timer = Timer.new()
	timer.autostart = false
	timer.one_shot = true
	timer.wait_time = 0.5
	timer.timeout.connect(timeout)
	add_child(timer)
	$Hitbox.gui_input.connect(hitbox_input)
	pass

func aura_on():
	$Aura.visible = true
	
func aura_off():
	$Aura.visible = false
	
func hitbox_input(event: InputEvent) -> void:
	if event is InputEventScreenTouch and event.is_pressed():
		timer.start()
	elif event is InputEventScreenTouch and event.is_released():
		if !timer.is_stopped():
			Utils.highlight_car(self)
		else: 
			SignalBus.charaPreviewed.emit(null)
		timer.stop()
		
func timeout():
	SignalBus.charaPreviewed.emit(self)
	pass
