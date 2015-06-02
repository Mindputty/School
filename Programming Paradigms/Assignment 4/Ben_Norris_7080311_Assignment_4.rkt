#lang racket

; question 1:

(define(threeD L)(sort-lists L '()))

(define (sort-lists L Lacc)
  (if (null? L)
      Lacc
      (let* ((head (car L))(tail (cdr L))(lacc2 (place-elem head Lacc)))(sort-lists tail lacc2))
      )
  )

(define (place-elem elem L)
  (if (null? L) 
      (list elem)
      ; else
      (cond 
        ; 1st condition (less than)
        [(< (list-ref elem 0) (list-ref (car L) 0))(cons elem L)]
        ; 2nd condition (equal)
        [(eq? (list-ref elem 0) (list-ref (car L) 0))
         (cond 
           ; 1st condition of second nested cond
           [(< (list-ref elem 1) (list-ref (car L) 1))(cons elem L)]
           ; 2nd condition of second nested cond
           [(eq? (list-ref elem 1) (list-ref (car L) 1))
            (if (< (list-ref elem 2) (list-ref (car L) 2))(cons elem L)
                (cons (car L)(place-elem elem (cdr L))))
            ]
           ; 3rd conditional of second nested cond
           [else (cons (car L)(place-elem elem(cdr L)))]
           )]
        ; 3rd condition (greater than)
        [else (cons (car L)(place-elem elem (cdr L)))] ; 3rd conditional of 2nd conditional
        )
      )
  )

; question 2:

(define t2
 '((14 . 1)
 ((7 . 2) () ((12 . 1) () ()))
 ((26 . 1)
 ((20 . 3) ((17 . 1) () ()) ())
 ((31 . 1) ((30 . 1) () ())
 ((35 . 1) () ())))))

(define (insertT value tree)
  (if(null? (car tree))
     (cons (cons value 1)tree)
     (cond ((< value (caar tree))(cons (car tree)(insertT value (cadr tree)))) ; left
           ((> value (caar tree))(cons (car tree)(insertT value (cddr tree)))) ; right
           ((= value (caar tree))(cons (cons(caar tree)(+ 1 (cdar tree)))(cdr tree))) ; equal
           )))

; question 3:
(define (reduce Func F0 L)
  (if (null? L)
      F0
      (Func (car L)
            (reduce Func F0 (cdr L)))
      )
  )

(define (q3 V)
  (if (vector? V)(reduce + 0 (map abs (vector->list V)))
      (reduce + 0 (map abs V))))