extends Control
class_name StatusEffect

@export var effect : Utils.STAT
@export var value : int

@export var turnCountdown : int;

var statString : String
var stat : Utils.STAT
var statChange : int
var chara : Chara

func _ready() -> void:
	SignalBus.begin_turn.connect(turn_passed)

func _init(targetChara : Chara, Stat : Utils.STAT, Value : int, Turns: int) -> void:
	self.turnCountdown = Turns
	self.stat = Stat
	chara = targetChara
	value = Value
	match stat:
		Utils.STAT.ATK: 
			statString = "Attack"
			statChange = int(chara.ATK * (Value * 0.01))
			chara.ATK = chara.ATK + statChange
		Utils.STAT.S_ATK: 
			statString = "SP.Attack"
			statChange = int(chara.S_ATK * (Value * 0.01))
			chara.S_ATK = chara.S_ATK + statChange
		Utils.STAT.DEF: 
			statString = "Defense"
			statChange = int(chara.DEF * (Value * 0.01))
			chara.DEF = chara.DEF + statChange
		Utils.STAT.S_DEF: 
			statString = "Sp. Defense"
			statChange = int(chara.S_DEF * (Value * 0.01))
			chara.S_DEF = chara.S_DEF + statChange
		Utils.STAT.SPD: 
			statString = "Speed"
			statChange = int(chara.SPD * (Value * 0.01))
			chara.SPD = chara.SPD + statChange
		Utils.STAT.CRT: 
			statString = "Crit Chance"
			statChange = Value
			chara.CRT = chara.CRT + statChange
		Utils.STAT.DGE: 
			statString = "Dodge Chance"
			statChange = Value
			chara.DGE = chara.DGE + statChange	
	pass


func turn_passed(_isPlayerTurn : bool):
	if turnCountdown != -1: turnCountdown = turnCountdown - 1;
	if turnCountdown == 0: remove()
		
func remove():
	print("[Status Effect] Removing SE from " + chara.NAME)
	match stat:
		Utils.STAT.ATK: chara.ATK = chara.ATK - statChange
		Utils.STAT.S_ATK: chara.S_ATK = chara.S_ATK - statChange
		Utils.STAT.DEF: chara.DEF = chara.DEF - statChange
		Utils.STAT.S_DEF: chara.S_DEF = chara.S_DEF - statChange
		Utils.STAT.SPD: chara.SPD = chara.SPD - statChange
		Utils.STAT.CRT: chara.CRT = chara.CRT - statChange
		Utils.STAT.DGE: chara.DGE = chara.DGE - statChange
	queue_free()
