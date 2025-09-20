extends Dialogue
class_name DialOption

var text : String
var next_dial : Dialogue

func _init(Text : String, Next_Dial : Dialogue):	
	text = Text
	next_dial = Next_Dial
	
