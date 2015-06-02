item('potato leek soup', 8,v).
item('marinated fig, pistachio, boston scarlet', 14,v).
item('grilled romaine, tomato jam, ricotta', 13,v).
item('gnocchi mushroom and cherry tomato', 15,v).
item('beef tartare, wasabi mayo', 16,m).
item('lamb merguez, cauliflower, cabbage', 16,m).
item('boar sausage and bourbon mustard', 15,m).
item('confit rabbit, orzo', 15,m).
item('lamb berbere and lentil', 13,m).
item('grilled hanger steak, frites, aioli', 16,m).
item('mussels apple butter', 13,s).
item('grilled squid and quinoa', 13,s).
item('mahi-mahi orange, star anise', 16,s).
item('Salmon, artichoke, leek, mustard', 18,s).
item('apple berry strudel', 7,d).
item('cheesecake, almond', 8,d).
item('boca negra, hazelnut gelato', 8,d).
item('bread pudding', 7,d).
item('chocolate pate', 8,d).
item('Pure gelato', 8,d).

saveMoney(X,Y,Z):- item(X,B,C), item(Y,D,E), Z is (B+D)-22.

vegetarianSuggestion(Price):- item(X,Y,v), item(A,B,d), Price is Y+B, writeln('****Suggestion****'),write('Entree: '),writeln(X),write('Dessert: '),writeln(A).

meal(paul(P1,P2),marie(M1,M2),jean(S1,S2)):- paul(P1,P2), marie(M1,M2), jean(J1,J2), areUnique(P1,P2,M1,M2,J1,J2),printMenus(P1,P2,M1,M2,J1,J2).
paul(X,A):- item(X,Y,Z), item(A,B,C), not(X=A).
marie(X,A):- item(X,Y,Z), item(A,B,C), not(Z=s), not(Z=d), not(C=s), not(C=d).
jean(X,A):- item(X,Y,m), item(A,B,d).
areUnique(A,B,C,D,E,F):- not(A=B), not(A=C), not(A=D), not(A=E), not(A=F), not(B=C), not(B=D), not(B=E), not(B=F), not(C=D), not(C=E), not(C=F), not(D=E), not(D=F), not(E=F).
printMenus(A,B,C,D,E,F):- writeln('****Paul\'s suggestions****'),writeln(A),writeln(B),writeln('****Marie\'s suggestions****'),writeln(C),writeln(D),writeln('****Jean\'s suggestions****'),writeln(E),writeln(F),nl.

chooseThree(X,X2,X3):- item(X,Y,Z), item(X2,Y2,Z2), item(X3,Y3,Z3), not(X=X2), not(X=X3), not(X2=X3), P is Y + Y2 + Y3, P < 30, write('Total Price: '), writeln(P).

tableDHote(X,A):- item(X,Y,Z), not(Z=d), item(A,B,d).