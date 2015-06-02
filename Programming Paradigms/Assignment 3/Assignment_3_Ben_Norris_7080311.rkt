#lang racket

;Question 1
(define(decode-rl L1) 
  (if
   (null? L1) 
   L1
   (append (buildList (car L1)(caar L1))(decode-rl(cdr L1)))
  )
)

(define (buildList B1 num) 
  (if(= num 1)(cdr B1)(append (cdr B1)(buildList B1 (- num 1)))))

;Question 2
(define (num2Digit num)(num2Digit-help num (nDigit num)))
(define (num2Digit-help num x)
  (if (= num 0) '()
      (append (list (quotient num (expt 10 (- x 1))))
              (num2Digit-help (remainder num (expt 10 (- x 1))) (- x 1)))))

(define (nDigit num)(calcDigit num 0))
(define (calcDigit num x)
  (if (= (modulo num (expt 10 x)) num) 0
      (+ 1 (calcDigit num (+ 1 x)))))

; Question 3 is not done in two parts (the following answer is for
; both 3.1 as well as 3.2)
(define (reduction num)
  (let ((x num)(s (sum (num2Digit num))))
    (if(>= s 10)(reduction s)
       s)))

(define (sum L)
   (if(null? L)
      0
      (+ (car L) (sum (cdr L)))))