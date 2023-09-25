import random
import time

sten = 0
sax = 0
pase = 0
minaPoang = 0
datornPoang = 0
forsok = 0

print("********************")
time.sleep(0.5)
print("Sten - Sax - Påse")
time.sleep(0.5)
print("********************")
time.sleep(0.5)
print("")

forsok = int(input("Hur många poäng behövs för att vinna? "))

def stenSaxPase():
    print("Gör ditt val:")
    print("1: Sten")
    print("2: Sax")
    print("3. Påse")