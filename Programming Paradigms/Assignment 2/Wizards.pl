coin(0).
coin(1).

bag(Bronze, Brass, Copper, Tin, E):-coin(Bronze),coin(Brass),coin(Copper),coin(Tin), 
E=[Bronze,Brass,Copper,Tin],(Bronze+Brass+Copper+Tin)<3, 
(Bronze+Brass+Copper+Tin)=\=0,((Bronze=:=1);(Brass=:=1)), 
((Tin=:=1);(Bronze=:=0)).

magicUser(X):- bag(_,_,_,_,X).

fewestCoins(E):- magicUser(A), magicUser(B), A\=B, magicUser(C), B\=C, A\=C,   
magicUser(D), A\=D, B\=D, C\=D, 
A=[A1,A2,A3,A4], B=[B1,B2,B3,B4], C=[C1,C2,C3,C4], D=[D1,D2,D3,D4],
((A1=\=B1, A1=\=C1, A1=\=D1);(A2=\=B2,A2=\=C2,A2=\=D2);(A3=\=B3,A3=\=C3,A3=\=D3);(A4=\=B4,A4=\=C4,A4=\=D4)),
((B1=\=A1, B1=\=C1, B1=\=D1);(B2=\=A2,B2=\=C2,B2=\=D2);(B3=\=A3,B3=\=C3,B3=\=D3);(B4=\=A4,B4=\=C4,B4=\=D4)),
writeln(''),
writeln('User: [Bronze, Brass, Copper, Tin]'), writeln('****************'),
write('Male Magician : '), writeln(A),
write('Wizard : '), writeln(B),
write('Female Magician : '), writeln(C),
write('Witch : '), writeln(D), writeln(''),
smallestBag(A,B,C,D,E).

smallestBag(A,_,_,_,E):- A =[A1,A2,A3,A4],(A1+A2+A3+A4)=:=1, E = A, write('The bearer of the smallest bag is the '),writeln('Male Magician'),writeln(''),!.
smallestBag(_,B,_,_,E):- B =[B1,B2,B3,B4],(B1+B2+B3+B4)=:=1, E = B, write('The bearer of the smallest bag is the '),writeln('Wizard'),writeln(''),!.
smallestBag(_,_,C,_,E):- C =[C1,C2,C3,C4],(C1+C2+C3+C4)=:=1, E = C, write('The bearer of the smallest bag is the '),writeln('Female Magician'),writeln(''),!.
smallestBag(_,_,_,D,E):- D =[D1,D2,D3,D4],(D1+D2+D3+D4)=:=1, E = D, write('The bearer of the smallest bag is the '),writeln('Witch'),writeln('').