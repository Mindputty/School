day( 4, 1, 'World Braille Day ').

day( 6, 1, 'Global Day for War Orphans').

day(21, 1, 'International Day of Hugs' ).

day(24, 1, 'World Day of Social Communications ').

day(26, 1, 'International Customs Day'). 

day(27, 1, 'International Day of Commemoration in Memory of the Victims of the
 Holocaust' ).

day(28, 1, 'Data Privacy Day').

day(2, 2,'World Wetlands Day').

day(4, 2,'World Cancer Day').

day(6, 2,'International Day of Zero Tolerance to Female Genital Mutilation').

day(8, 2,'World Day without Mobile Phones').

day(12, 2,'International Day against the Use of Child Soldiers').

day(13, 2,'World Radio Day').

day(14, 2,'International Congenital Heart Disease Awareness Day').

day(15, 2,'International Childhood Cancer Day').
day(20, 2,'World Day of Social Justice').

day(21, 2,'International Mother Language Day').

day(22, 2,'World Thinking Day').

day( 1, 3, 'World Civil Defence Day').

day( 3, 3, 'World Wildlife Day').

day( 4, 3, 'World Tennis Day').

day( 8, 3, 'International Women\'s Day').

day(11, 3, 'World Plumbing Day').

day(12, 3, 'World Day Against Cyber Censorship').

day(14, 3, 'Pi Day').

day(15, 3, 'World Consumer Rights Day').

day(20, 3, 'International Day of Happiness').

day(21, 3, 'International Day for the Elimination of Racial Discrimination').

day(22, 3, 'World Water Day').

day(23, 3, 'World Meteorological Day').

day(24, 3, 'World Tuberculosis Day').

day(25, 3, 'International Day of Remembrance of the Victims of Slavery and the Transatlantic Slave Trade').

day(27, 3, 'World Theatre Day' ).



ndays(1,31).

ndays(2,28).

ndays(3,31).

ndays(4,30).

ndays(5,31).

ndays(6,30).

ndays(7,31).

ndays(8,31).

ndays(9,30).

ndays(10,31).

ndays(11,30).

ndays(12,31).



days_of_the_month(Month,L):- days_of_the_month(Month, 1, L), writeln(L), fail.
days_of_the_month(Month, Day, L):- ndays(Month,Day), \+day(Day,Month,X), L=[],!.
days_of_the_month(Month, Day, L):- ndays(Month,Day), day(Day,Month,X), L=[(Day,X)],!.
days_of_the_month(Month, Day, L):- DD is Day+1, days_of_the_month(Month, DD, LL), \+day(Day,Month,Y), L = LL,!.
days_of_the_month(Month, Day, L):- DD is Day+1, days_of_the_month(Month, DD, LL), day(Day,Month,Y), L = [(Day,Y)|LL].


nondedicated_days(Month, L):- nondedicated_days(Month, 1, L), write('Non-dedicated Days: '),writeln(L),fail.
nondedicated_days(Month, Day, L):- ndays(Month,Day), day(Day,Month,_),L = [], !.
nondedicated_days(Month, Day, L):- ndays(Month,Day), \+day(Day,Month,_),L = [Day], !.
nondedicated_days(Month, Day, L):- DD is Day + 1, nondedicated_days(Month, DD,LL), \+day(Day,Month,_), L = [Day|LL],!.
nondedicated_days(Month, Day, L):- DD is Day + 1, nondedicated_days(Month, DD,LL), day(Day,Month,_), L = LL.

free_days(D,L):- free_days(D,1,L).
free_days(Day,Month,L):- Month > 12, L=[],!.
free_days(Day,Month,L):- MM is Month + 1, free_days(Day,MM,LL), \+day(Day, Month, _), L = [Month|LL],!.
free_days(Day,Month,L):- MM is Month + 1, free_days(Day,MM,LL), day(Day, Month, _), L = LL.