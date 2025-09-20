extends Control

var Chara1 : CharaRes = API.current_team[0]
var Chara2 : CharaRes = API.current_team[1]
var Chara3 : CharaRes = API.current_team[2]

var turnCounter : int = 0;

var teamHP : int = 0;
var maxTeamHP : int = 0;

var hasCounter = false
var isGUIHidden = false
 
func _ready() -> void:	
	# Setup Signals
	SignalBus.abilityActivated.connect(abilityActivated)
	SignalBus.take_damage.connect(take_dmg)
	SignalBus.heal.connect(heal)
	SignalBus.begin_turn.connect(begin_turn)
	SignalBus.darken_sky.connect(darken_sky)
	
	#Setup BGM
	$Audio.play_bgm(load("res://ost/Against The Wire.ogg"))
	
	#Setup Characeters & Cars
	$Charas/Chara.setup_chara(Chara1)
	print(Chara1.PASSIVE != null)
	if Chara1.PASSIVE != null: 
		$Charas/Chara/Passive.set_script(Chara1.PASSIVE)
		$Charas/Chara/Passive._ready()
	$UI/Highway.add_car($Charas/Chara)
	
	$Charas/Chara2.setup_chara(Chara2)
	print(Chara2.PASSIVE != null)
	if Chara2.PASSIVE != null: 
		$Charas/Chara2/Passive.set_script(Chara2.PASSIVE)
		$Charas/Chara2/Passive._ready()
	$UI/Highway.add_car($Charas/Chara2)
	
	$Charas/Chara3.setup_chara(Chara3)
	print(Chara3.PASSIVE != null)
	if Chara3.PASSIVE != null: 
		$Charas/Chara3/Passive.set_script(Chara3.PASSIVE)
		$Charas/Chara3/Passive._ready()
	$UI/Highway.add_car($Charas/Chara3)
	
	#Setup Team HP
	teamHP = (Chara1.STATS.HP + Chara2.STATS.HP + Chara3.STATS.HP) * 3
	maxTeamHP = teamHP
	update_hp()

	# Setup Stage 
	if Utils.current_level == "":
		Utils.current_level = "res://stages/nhc/nhc_0_00.gd"
	$Enemies.set_script(load(Utils.current_level))
	$Enemies.level_setup()
	if $Enemies.Enemies.size() != 0: $Enemies.spawn_next_enemy()
	if $Enemies.Enemies.size() != 0: $Enemies.spawn_next_enemy()
	if $Enemies.Enemies.size() != 0: $Enemies.spawn_next_enemy()
	
	# Setup Backdrop
	$BG.add_child($Enemies.Backdrop.instantiate())
	
	$Enemies.select_next_enemy()
	
	# Setup Player Deck
	for card in Chara1.ABILITY_CARDS: $UI/Deck.add(card, $Charas/Chara)
	for card in Chara2.ABILITY_CARDS: $UI/Deck.add(card, $Charas/Chara2)
	for card in Chara3.ABILITY_CARDS: $UI/Deck.add(card, $Charas/Chara3)
	
	$UI/Deck.draw()
	$UI/Deck.draw()
	$UI/Deck.draw()
	$UI/HUD.updateNextCard($UI/Deck.Rest[0])
	
	$UI/Highway.start()
	$UI/Deck.lock_hand()
	
	if $Charas/Chara/Passive.get_script() != null: $Charas/Chara/Passive.battle_start()
	if $Charas/Chara2/Passive.get_script() != null: $Charas/Chara2/Passive.battle_start()
	if $Charas/Chara3/Passive.get_script() != null: $Charas/Chara3/Passive.battle_start()
	pass
		
func abilityActivated(abilityCard : AbilityCard, isCounter : bool = false):
	$Camera.enabled = true
	$UI/Deck.lock_hand()
	if !isCounter: 
		$UI/Deck.put_back(abilityCard)
	if !isGUIHidden:
		$UI/AP.play("HideUI")
		isGUIHidden = true
	
	var EnemyAbilityCard : EnemyCard = $UI/EnemyDeck.put_back_current_card()
	var turnOwner : Chara = $UI/Highway.currentTurnCar.carOwner
	
	if turnOwner is CharaPlayer or isCounter:
		if(!isCounter and $UI/Highway.currentTurnCar.carOwner != abilityCard.charaOwner):
			# Focus on whose Turn it is.
			$Camera.focus($UI/Highway.currentTurnCar.carOwner)
			$Charas.bring_forth($UI/Highway.currentTurnCar.carOwner)
			await get_tree().create_timer(0.5).timeout
			$UI/Highway.currentTurnCar.carOwner.play_anim(Utils.ANIM.MAGIC)
			$ChaBar.anim_start($UI/Highway.currentTurnCar.carOwner.global_position.y,"SWITCH!")
			$Audio.play_voice(turnOwner, VoiceOver.TYPE.SWITCH)
			await get_tree().create_timer(1.0).timeout 
		
		# Focus on whose Card it is.
		$Camera.focus(abilityCard.charaOwner)
		$Charas.bring_forth(abilityCard.charaOwner)
		await get_tree().create_timer(0.5).timeout
		
		$ChaBar.anim_start(abilityCard.charaOwner.global_position.y,"GOT IT!")
		abilityCard.charaOwner.play_anim(Utils.ANIM.SWING)
		abilityCard.before_ability_effect()
		
		$Audio.play_voice(abilityCard.charaOwner, VoiceOver.TYPE.SWITCH_IN)
		await get_tree().create_timer(1).timeout
		
		# Showcase Splash
		$UI/CardPreviewScreen.showcase_damage()
		$UI/CardPreviewScreen.setup(abilityCard.charaOwner,abilityCard.cardQuote)
		$Audio.play_voice(abilityCard.charaOwner, VoiceOver.TYPE.SPLASH)
		await get_tree().create_timer(2.5).timeout
		
		var final_dmg
		if abilityCard.cardTarget != Utils.TARGET.ALL_ALLIES:
			
			# Focus on Enemy Taking Damage
			if(abilityCard.cardTarget == Utils.TARGET.ALL_ENEMIES):
				$Camera.focus_all_enemies()
				await get_tree().create_timer(0.5).timeout
				final_dmg = Utils.damage_all_enemies(abilityCard)
			else:
				$Camera.focus(Utils.SelectedEnemy)
				$Enemies.bring_forth(Utils.SelectedEnemy)
				await get_tree().create_timer(0.5).timeout
				final_dmg = Utils.damage(abilityCard, Utils.SelectedEnemy)
			SignalBus.enemy_damage_dealt.emit(abilityCard, final_dmg)
			$Audio.play_voice(Utils.SelectedEnemy, VoiceOver.TYPE.HURT)
			$Audio.play_sfx(load("res://art/effects/explosion.wav"))
			await get_tree().create_timer(1).timeout
			abilityCard.after_ability_effect()	
		else:
			abilityCard.charaOwner.play_anim(Utils.ANIM.MAGIC)
			$Audio.play_sfx(load("res://ost/heal.wav"))
			await get_tree().create_timer(1).timeout
			abilityCard.after_ability_effect()	
	else:
		if($UI/Highway.currentTurnCar.carOwner != EnemyAbilityCard.cardOwner):
			# Focus on whose Turn it is.
			$Camera.focus($UI/Highway.currentTurnCar.carOwner)
			$Enemies.bring_forth($UI/Highway.currentTurnCar.carOwner)
			await get_tree().create_timer(0.5).timeout
			$UI/Highway.currentTurnCar.carOwner.play_anim(Utils.ANIM.MAGIC)
			$Audio.play_voice($UI/Highway.currentTurnCar.carOwner, VoiceOver.TYPE.SWITCH)
			await get_tree().create_timer(1).timeout 
		
		# Focus on whose Card it is.
		$Camera.focus(EnemyAbilityCard.cardOwner)
		$Enemies.bring_forth(EnemyAbilityCard.cardOwner)
		await get_tree().create_timer(0.5).timeout
		EnemyAbilityCard.cardOwner.play_anim(Utils.ANIM.SWING)
		$Audio.play_voice($UI/Highway.currentTurnCar.carOwner, VoiceOver.TYPE.SWITCH_IN)
		abilityCard.before_defending_effect(EnemyAbilityCard)
		await get_tree().create_timer(1).timeout
		
		# Showcase Damage.
		$UI/CardPreviewScreen.showcase_damage()
		$UI/CardPreviewScreen.setup(EnemyAbilityCard.cardOwner,EnemyAbilityCard.cardQuote)
		$Audio.play_voice($UI/Highway.currentTurnCar.carOwner, VoiceOver.TYPE.SPLASH)
		await get_tree().create_timer(2.5).timeout
		
		# Focus on Player Taking Damage
		$Camera.focus(abilityCard.charaOwner)
		$Charas.bring_forth(abilityCard.charaOwner)
		await get_tree().create_timer(0.5).timeout
		Utils.create_effect(Utils.EFFECT.BOOM,abilityCard.charaOwner.global_position,3)
		
		var enemy_dmg = Utils.calc_enemy_damage(EnemyAbilityCard, abilityCard)
		if str(enemy_dmg) == "Dodged": 
			if !abilityCard.hasCounter:
				abilityCard.charaOwner.play_anim(Utils.ANIM.MAGIC)
			else:
				abilityCard.charaOwner.play_anim(Utils.ANIM.MAGIC,1.0)
		else :
			if !abilityCard.hasCounter:
				abilityCard.charaOwner.play_anim(Utils.ANIM.DAMAGE)
			else:
				abilityCard.charaOwner.play_anim(Utils.ANIM.DAMAGE,1.0)
				
			$Audio.play_sfx(load("res://art/effects/explosion.wav"))
			
		$UI/PlayerDMG.show_dmg(enemy_dmg)
		$Audio.play_voice(abilityCard.charaOwner,VoiceOver.TYPE.HURT)
		if hasCounter:
			await get_tree().create_timer(1).timeout
			$ChaBar.anim_start(abilityCard.charaOwner.global_position.y,"COUNTER")
		await get_tree().create_timer(1).timeout
		abilityCard.after_defending_effect(EnemyAbilityCard)
		if abilityCard.hasCounter: return
		
	abilityCard.charaOwner.aura_off()
	$Camera.reset()
	$Charas.reset()
	$Enemies.reset()

	$UI/AP.play("ShowUI")
	isGUIHidden = false
	await $UI/AP.animation_finished
	$Camera.enabled = false

	SignalBus.end_turn.emit()

	$UI/Deck.draw()
	$UI/InfoBoard.show_wait()
	$UI/Highway.start()	
	pass

# Show Debug Deck List
#func _process(_delta: float) -> void:
	#var string = "Hand:\n"
	#for card : AbilityCard in $Deck.Hand:
		#string += " - " + card.cardName + "\n"
	#string += "\nDeck:\n"
	#for card : AbilityCard in $Deck.Rest:
		#string += " - " + card.cardName + "\n"
	#$Deck/DeckList.text = string

func begin_turn(isPlayersTurn : bool):
	turnCounter += 1
	$"UI/InfoBoard/HBOX/Turn Counter".text = "Turn  " + str(turnCounter)
	
	if isPlayersTurn : 
		print("[Battleground] Begin Player Turn")
		$UI/InfoBoard.show_player_turn()
		$UI/Deck.unlock_hand()
	else : 
		print("[Battleground] Begin Enemy Turn")
		$UI/InfoBoard.show_enemy_turn()
		$UI/EnemyDeck.play_a_card()
		$UI/Deck.unlock_hand()
	
func take_dmg(dmg : int, is_proc : bool):
	if is_proc:
		print("[System] Taking "+str(dmg)+"% damage.")
		dmg = int(teamHP * (dmg*0.01))
	teamHP = teamHP - dmg
	update_hp()
	if teamHP <=0:
		$UI.add_child(load("res://scenes/battlegrounds/NDE_LosingScreen.tscn").instantiate())
		
func heal(amount : int, is_proc : bool):
	if is_proc:
		print("[System] Taking "+str(amount)+"% damage.")
		amount = int(teamHP * (amount*0.01))
	teamHP = teamHP + amount
	update_hp()

func update_hp():
	var prc : int = int(float(teamHP) / float(maxTeamHP) * 100)
	$UI/HUD/HP_Count.text = "("+ str(teamHP) +"/"+ str(maxTeamHP) +") " + str(prc) + "%"
	$UI/HUD/TextureProgressBar.value = prc
	
func darken_sky(darken : bool):
	if darken:
		$Audio.play_sfx(load("res://art/effects/ThunderSFX.ogg"))
		create_tween().tween_property($BG,"modulate",Color("989898"),0.5)
	else:
		create_tween().tween_property($BG,"modulate",Color("ffffff"),0.5)
	
func _input(event: InputEvent) -> void:
	if event.is_action_pressed("Draw"):
		get_tree().reload_current_scene()


func _on_menu_b_pressed() -> void:
	$UI.add_child(load("res://scenes/battlegrounds/BattleMenu.tscn").instantiate())
	
	pass # Replace with function body.
