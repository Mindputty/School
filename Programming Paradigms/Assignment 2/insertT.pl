t(value, frequency).

insertT(V, nil, NT):- NT = t((V,1), nil, nil).
insertT(V, T, NT) :- T = t(Root, Left, Right), Root = (VV,F), V < VV, insertT(V, Left, NNT), NT = t(Root, NNT, Right).
insertT(V, T, NT) :- T = t(Root, Left, Right), Root = (VV,F), V > VV, insertT(V, Right, NNT), NT = t(Root, Left, NNT).
insertT(V, T, NT) :- T = t(Root, Left, Right), Root = (VV,F), V =:= VV, FF is F+1, NT = t((VV,(FF)),Left,Right).