extends TextureRect
class_name Highway

var PreloadedCar : PackedScene = preload("res://scenes/battlegrounds/NDE_Car.tscn")

var proceeding : bool = false
var currentTurnCar : Car 

func _ready() -> void:
	SignalBus.create_car.connect(add_car)
	SignalBus.create_additional_car.connect(add_car)
	SignalBus.enemy_died.connect(remove_car)

func add_car(chara : Chara, custom_speed : int = -1):
	var car : Car = PreloadedCar.instantiate()
	car.position = Vector2(20,51)
	car.carOwner = chara
	car.carSpeed = custom_speed
	
	print("[Highway] Created a Car for: " + str(chara.NAME))
	
	if car.carOwner is CharaEnemy:
		car.set("self_modulate",Color.RED)
	
	car.get_node("CLIP/ICON").texture = chara.SPLASH
	car.get_node("CLIP/ICON").position = chara.MISC.CarSplashOffset + Vector2(-30,0)
	add_child(car)

func start():
	for car : Car in get_children():
		if car.position.x == 555:
			car.position.x = 20
			car.scale = Vector2(1,1)
			
	proceeding = true
	
func stop():
	proceeding = false
	
func remove_car(enemy : CharaEnemy):
	for car : Car in get_children():
		if car.carOwner == enemy:
			car.queue_free()
	
func _process(_delta: float) -> void:
	if proceeding:
		for car : Car in get_children():
			
			match car.carSpeed:
				-1: car.position.x += _delta * car.carOwner.SPD
				_:  car.position.x += _delta * car.carSpeed
				
			car.z_index = int(0-(555 - car.position.x)/10)
			
			if car.position.x > 555:
				currentTurnCar = car
				stop()
				
				for other_car : Car in get_children():
					if other_car != car and other_car.position.x > 555:
						other_car.position.x = 550
					
				car.position.x = 555
				create_tween().tween_property(car,"scale",Vector2(1.5,1.5),0.2).set_trans(Tween.TRANS_SINE)
				car.flash()
				Utils.TurnOwner = car.carOwner
				SignalBus.begin_turn.emit(car.carOwner is CharaPlayer)
				
	
