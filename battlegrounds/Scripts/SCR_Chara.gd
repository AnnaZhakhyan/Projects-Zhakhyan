extends Control
class_name Chara

var charaRes : CharaRes

# -------------------------------------
var NAME : String # Name of the Character
var TITLE : String # Title of the Character
# -------------------------------------
var COLOR : Utils.COLOR # Type of the Character
var RARITY : int # Rarity of the Character
# -------------------------------------
var ATK : int # Attack
var S_ATK : int # Special Attack
var DEF : int # Defense
var S_DEF : int # Special Defense
var SPD : int # Speed
var CRT : int # Critical Chance
var DGE : int # Dodge
# -------------------------------------
var PERKS : PerkList # List of Perks of the Character
# -------------------------------------
var FRAMES : SpriteFrames # List of Perks of the Character
var SPLASH : Texture2D # List of Perks of the Character
var VOICE_OVER : VoiceOver # All Character's Voice Lines
var MISC : MiscCharaValues # Miscellaneous Values of the Character
# -------------------------------------
var ENEMY_CARDS : Array[EnemyCardRes]# Ability cards of the Enemy

var HP : float
var MAX_HP : float

func setup_chara(chara : CharaRes):
	NAME = chara.NAME
	TITLE = chara.TITLE
	COLOR = chara.COLOR
	RARITY = chara.RARITY
	ATK = chara.STATS.ATK
	S_ATK = chara.STATS.S_ATK
	DEF = chara.STATS.DEF
	S_DEF = chara.STATS.S_DEF
	SPD = chara.STATS.SPD
	CRT = chara.STATS.CRT
	DGE = chara.STATS.DGE
	PERKS = chara.PERKS
	SPLASH = chara.SPLASH
	MISC = chara.MISC
	VOICE_OVER = chara.VOICE_OVER
	
	$Sprite.sprite_frames = chara.FRAMES
	$Sprite.play("IDLE")
	charaRes = chara
	
	match COLOR:
		Utils.COLOR.AGL: $ColorCircle.modulate = Color("8cd1ff"); pass;
		Utils.COLOR.STR: $ColorCircle.modulate = Color("ff8c8c"); pass;
		Utils.COLOR.PHY: $ColorCircle.modulate = Color("ffdd8c"); pass;
		Utils.COLOR.INT: $ColorCircle.modulate = Color("bc75ff"); pass;
		Utils.COLOR.TEQ: $ColorCircle.modulate = Color("b1ff8c"); pass;

func setup_enemy(enemy : EnemyRes):
	NAME = enemy.NAME
	TITLE = enemy.TITLE
	COLOR = enemy.COLOR
	ATK = enemy.STATS[0]
	S_ATK = enemy.STATS[1]
	DEF = enemy.STATS[2]
	S_DEF = enemy.STATS[3]
	SPD = enemy.STATS[4]
	HP = enemy.STATS[5]
	CRT = enemy.STATS[6]
	DGE = enemy.STATS[7]
	SPLASH = enemy.SPLASH
	VOICE_OVER = enemy.VOICE_OVER
	MISC = enemy.MISC
	ENEMY_CARDS = enemy.CARDS
	HP = float(HP)
	MAX_HP = HP
	$Sprite.sprite_frames = enemy.FRAMES
	
	SignalBus.add_enemy_cards.emit(self)
	SignalBus.create_car.emit(self)
	
	for car : int in enemy.ADDITIONAL_CARS:
		SignalBus.create_additional_car.emit(self, car)

func play_anim(anim : Utils.ANIM, waitTime : float = 0):
	$Sprite.animation = $Sprite.sprite_frames.get_animation_names()[anim]
	await $Sprite.animation_finished
	await get_tree().create_timer(waitTime).timeout
	$Sprite.play($Sprite.sprite_frames.get_animation_names()[Utils.ANIM.IDLE])
	pass

func get_stacks(stackName : String) -> int:
	for child in get_children():
		if child is Stack: 
			if child.stackName == stackName:
				return child.value
	return 0;
