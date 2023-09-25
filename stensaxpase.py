import random

def mittVal():
    print("Gör ditt val (sten / sax / påse)")
    print("1. Sten")
    print("2. Sax")
    print("3. Påse")
    val = input().lower() #lower() gör så att strängens alla bokstäver är småa
    if val not in ["sten", "sax", "påse"]:
        print("Svara på nytt (sten / sax / påse)")
        return mittVal()
    return val

def datorVal():
    return random.choice(["sten", "sax", "påse"]) #datorn väljer mellan de tre olika valen

def checkResults(user, computer):
    if user == computer:
        return "Oavgjort"
    elif (user == "sten" and computer == "sax") or \
         (user == "påse" and computer == "sten") or \
         (user == "sax" and computer == "påse"):
        return "Datorn valde", computer, ", du valde", user,". Du vann"
    else:
        return "Datorn valde", computer, ", du valde", user,". Datorn vann"

def stenSaxPase():
    user = mittVal()
    comp = datorVal()
    print(checkResults(user, comp))

stenSaxPase()