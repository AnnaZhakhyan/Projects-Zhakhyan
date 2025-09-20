extends Control
class_name Deck

@export var AllOutAttacks : bool = false

var Hand : Array[AbilityCard] = [null,null,null]
var Rest : Array[AbilityCard]

var preloadedAbilityCard : PackedScene = preload("res://scenes/battlegrounds/NDE_AbilityCard.tscn")
var pos : Vector2 = Vector2(1371.601, -1549.781)

func _ready() -> void:
	SignalBus.begin_turn.connect(all_out)
	pass

func draw():
	if Hand[0] == null :
		Hand[0] = Rest[0]
		Hand[0].position = $Card1.position
		Hand[0].og_pos = $Card1.position
		Hand[0].scale = Vector2(0.915,0.915)
		Hand[0].is_movable = true;
		Hand[0].enter_hand_effect()
		Rest.remove_at(0)
	if Hand[1] == null :
		Hand[1] = Rest[0]
		Hand[1].position = $Card2.position
		Hand[1].og_pos = $Card2.position
		Hand[1].scale = Vector2(0.915,0.915)
		Hand[1].is_movable = true;
		Hand[1].enter_hand_effect()
		Rest.remove_at(0)
	if Hand[2] == null :
		Hand[2] = Rest[0]
		Hand[2].position = $Card3.position
		Hand[2].og_pos = $Card3.position
		Hand[2].scale = Vector2(0.915,0.915)
		Hand[2].is_movable = true;
		Hand[2].enter_hand_effect()
		Rest.remove_at(0)
	else:
		print("Hand Full!")
	
	Rest[0].is_movable = false;
	Rest[0].position = $Card4.position
	Rest[0].og_pos = $Card4.position
	Rest[0].scale = Vector2(0.64,0.64)

func lock_hand():
	for card : AbilityCard in Hand:
		if card != null: card.is_movable = false
		
func unlock_hand():
	for card : AbilityCard in Hand:
		if card != null: card.is_movable = true
		
func all_out(_is_player_turn : bool):
	if Hand[0].cardOwner == Hand[1].cardOwner and Hand[1].cardOwner == Hand[2].cardOwner and AllOutAttacks:
		Hand[0].power_up(); Hand[1].power_up(); Hand[2].power_up()
		Hand[0].charaOwner.aura_on()
		Utils.add_buff(Hand[0].charaOwner,Utils.STAT.ATK,100,1)
		Utils.add_buff(Hand[0].charaOwner,Utils.STAT.S_ATK,100,1)
		SignalBus.darken_sky.emit(true)
	else:
		SignalBus.darken_sky.emit(false)
		if Hand[0] != null: Hand[0].power_down(); 
		if Hand[1] != null: Hand[1].power_down(); 
		if Hand[2] != null: Hand[2].power_down()
	pass
 
func add(abilityCard : Script, charaOwner : Chara):
	
	var card = preloadedAbilityCard.instantiate() 
	card.set_script(abilityCard) # Set Script
	$Cards.add_child(card) 
	card.position = pos 
	card.scale = Vector2(0.915,0.915)
	card.charaOwner = charaOwner
	card.setup()
	
	Rest.append(card)
	Rest.shuffle()

func put_back(abilityCard : AbilityCard):
	Hand[Hand.find(abilityCard)] = null
	Rest.append(abilityCard)
	abilityCard.position = pos
