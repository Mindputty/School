male(paul).
male(sam).
male(claude).
male(joe).
male(ken).
male(bill).
male(mike).
female(emma).
female(sophie).
female(jeanne).
female(emilie).
female(eva).
female(katy).

matchMixed(team(A,B),team(C,D)):- team(A,B), team(C,D), areUnique(A,B,C,D),nl.
areUnique(A,B,C,D):- not(A=B), not(A=C), not(A=D), not(B=C), not(B=D), not(C=D).
team(X,Y):- male(X),female(Y).
team(X,Y):- female(X),male(Y).


