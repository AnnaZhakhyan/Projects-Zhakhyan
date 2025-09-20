extends Control

var PreloadedEnemyCard : PackedScene = preload("res://scenes/battlegrounds/NDE_EnemyCard.tscn")
var EnemyDeck : Array[TextureRect]

var current_card : TextureRect

# Setup Signals ----------------
func _ready() -> void:
	SignalBus.add_enemy_cards.connect(add_cards)
	SignalBus.enemy_died.connect(remove_enemies_cards)

# Add Cards to Enemy Deck ----------------
func add_cards(chara : CharaEnemy):
	for CardRes in chara.ENEMY_CARDS:
		var card : EnemyCard = PreloadedEnemyCard.instantiate()
		card.setup(CardRes, chara)
		card.position = Vector2(1500,1500)
		add_child(card)
		
		EnemyDeck.append(card)
		EnemyDeck.shuffle()
	pass


# Select a Card from Enemy Deck ----------------
func play_a_card():
	var x : int = randi_range(0,2)
	current_card = EnemyDeck[x]
	SignalBus.enemy_card_played.emit(current_card)
	EnemyDeck.remove_at(x)
	current_card.get_node("AP").play("SPAWN")
	current_card.position.x = 0;
	EnemyDeck.append(current_card)
	pass


# Put the current card back in the Enemy Deck ----------------
func put_back_current_card():
	for child in get_children():
		if child.position != Vector2(1500,1500):
			child.position = Vector2(1500,1500)
			
	if current_card != null:
		var card = current_card
		current_card = null
		return card
	return null
	
func remove_enemies_cards(enemy : CharaEnemy):
	for child : EnemyCard in get_children():
		if child.cardOwner == enemy:
			print("[System] Removed " + child.cardName + "from Enemy Deck")
			EnemyDeck.remove_at(EnemyDeck.find(child))
			child.queue_free()
