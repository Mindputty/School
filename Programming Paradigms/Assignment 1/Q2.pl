reduction(X,R):- X<10, R=X, !.
reduction(X,R):- X1 is (X mod 10), reduction((X//10),X2), R is (X1+X2).

theosophicalReduction(X,R):- X<10, R=X, !.
theosophicalReduction(X,R):- X1 is (X mod 10), X2 is (X//10), R1 is (X2+X1), theosophicalReduction(R1, R),!.

numberHarmony(L,H):- between(L,H,K), K >= L, K=<H, theosophicalReduction(K,R), R = 6, writeln(K).
