/*
CS-4110 AS3 Wumpus World
Yuki Yamada (138557)

   +---+---+---+---+
 4 |   |   |   | P |
   +---+---+---+---+
 3 | W | G | P |   |
   +---+---+---+---+
 2 |   |   |   |   |
   +---+---+---+---+
 1 | S |   | P |   |
   +---+---+---+---+
     1   2   3   4
*/


map :- showmap.
showmap :- write("
   +---+---+---+---+
 4 |   |   |   | P |
   +---+---+---+---+
 3 | W | G | P |   |
   +---+---+---+---+
 2 |   |   |   |   |
   +---+---+---+---+
 1 | S |   | P |   |
   +---+---+---+---+
     1   2   3   4  \n\n").

/*World Map*/
at([1,3], wumpus).
at([2,3], gold).
at([3,1], pit).
at([3,3], pit).
at([4,4], pit).


/*Perceive infomation[stench, breeze, glitter] of a square*/
perceive(U, V, Info) :- perceiveX([U,V], Info).
perceiveX([U,V], [Stench, Breeze, Glitter]) :- valid(U,V), checkstench([U,V], [Stench,_,_]),!, checkbreeze([U,V], [_,Breeze,_]),!, checkglitter([U,V],[_,_,Glitter]),! .

checkstench([U,V], [Stench,_,_])  :- stench([U,V]), fillstench([Stench,_,_]),!; fillnone([Stench,_,_]).
checkbreeze([U,V], [_,Breeze,_])  :- breeze([U,V]), fillbreeze([_,Breeze,_]),!;  fillbreezenone([_,Breeze,_]).
checkglitter([U,V],[_,_,Glitter]) :- glitter([U,V]), fillglitter([_,_,Glitter]),!; fillglitternone([_,_,Glitter]).



/*Predicates for filling percepts*/
fillstench([stench,_,_]).
fillbreeze([_,breeze,_]).
fillglitter([_,_,glitter]).
fillnone([none,_,_]).
fillbreezenone([_,none,_]).
fillglitternone([_,_,none]).

/*Predicates to check if a square is stench, breeze andor glitter */
stench([U,V])  :- north([U,V], [P,Q]), at([P,Q], wumpus).
stench([U,V])  :- south([U,V], [P,Q]), at([P,Q], wumpus).
stench([U,V])  :- east( [U,V], [P,Q]), at([P,Q], wumpus).
stench([U,V])  :- west( [U,V], [P,Q]), at([P,Q], wumpus).
stench([U,V])  :- at([U,V], wumpus).

breeze([U,V])  :- north([U,V], [P,Q]), at([P,Q], pit).
breeze([U,V])  :- south([U,V], [P,Q]), at([P,Q], pit).
breeze([U,V])  :- east( [U,V], [P,Q]), at([P,Q], pit).
breeze([U,V])  :- west( [U,V], [P,Q]), at([P,Q], pit).
breeze([U,V])  :- at([U,V], pit).

glitter([U,V])  :- at([U,V], gold).


/*change each direction NSEW*/
north([U,V], [I,V]) :- I is U + 1.
south([U,V], [I,V]) :- I is U - 1.
east( [U,V], [U,J]) :- J is V + 1.
west( [U,V], [U,J]) :- J is V - 1.



/*Check if coordinate is valid on a defined map*/
valid(U,V) :- 0<U, U<5, 0<V, V<5.


/*Assertion predicates*/
add_safe(U, V)  :- valid(U,V), assert(safe(U,V)).
add_gold(U,V)   :- valid(U,V), assert(gold(U,V)).
add_wumpus(U,V) :- valid(U,V), assert(wumpus(U,V)).
add_pit(U,V)    :- valid(U,V), aassert(pit(U,V)).

add_possiblepit(U,V)    :- valid(U,V), assert(possiblepit(U,V)).
add_possiblewumpus(U,V) :- valid(U,V), assert(possiblewumpus(U,V)).

add_nopit(U,V)        :- valid(U,V), assert(nopit(U,V)).
add_nowumpus(U,V)     :- valid(U,V), assert(nowumpus(U,V)).

/*check if a square is safe*/
issafe(U,V) :- safe(U,V), !; nopit(U,V), nowumpus(U,V).

/*fail cases*/
safe(-1,-1).
nopit(-1,-1).
nowumpus(-1,-1).

/*Retraction predicates*/
remove_possiblepit(U,V)    :- valid(U,V), retract(possiblepit(U,V)).
remove_possiblewumpus(U,V) :- valid(U,V), retract(possiblewumpus(U,V)).



/*predicate to check two terms are same*/
same(X,X).
