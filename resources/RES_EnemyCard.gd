extends TextureRect
class_name EnemyCardRes

var cardOwner : String
var cardName : String
var cardPower : int
var cardColor : Utils.COLOR
var cardType : Utils.TYPE
var cardDesc : String
var cardQuote : String

var charaOwner : Chara

func _init(
	CardOwner : String,
	CardName : String,
	CardPower : int,
	CardColor : Utils.COLOR,
	CardType : Utils.TYPE,
	CardQuote : String) -> void:
	self.cardOwner = CardOwner
	self.cardName = CardName
	self.cardPower = CardPower
	self.cardType = CardType
	self.cardColor = CardColor
	self.cardQuote = CardQuote
	
