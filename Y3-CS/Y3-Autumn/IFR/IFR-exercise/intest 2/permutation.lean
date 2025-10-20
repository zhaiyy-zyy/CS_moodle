/-
COMP2068-IFR Exercise 
(Permutation) 
-/

namespace ex7

open list
set_option pp.structure_projections false
variables {A B C : Type}

/-
In the lecture we have introduced the function reverse
(and the auxilliary function snoc)
-/

def snoc : list A → A → list A
| [] a := [a]
| (a :: as) b := a :: (snoc as b)

def rev : list A → list A
| [] := []
| (a :: as) := snoc (rev as) a

/-
Show that rev (or fastrev) does actually outputs a permutation
of its input. 

I include the definition of Perm (and the auxilliary Insert)
here. 

Hint : You will need to state and prove a lemma about snoc.
-/

inductive Insert : A → list A → list A → Prop
| ins_hd : ∀ a:A, ∀ as : list A,Insert a as (a :: as)
| ins_tl : ∀ a b:A, ∀ as as': list A, Insert a as as' 
        → Insert a (b :: as) (b :: as')

inductive Perm : list A → list A → Prop 
| perm_nil : Perm [] []
| perm_cons : ∀ a : A, ∀ as bs bs' : list A,Perm as bs 
      → Insert a bs bs' → Perm (a :: as) bs'

open Insert
open Perm
/- --- Do not add/change anything above this line --- -/


-- proof that rev function does not change the elements in the given list
theorem perm_rev : ∀ as : list A, Perm as (rev as) := sorry


end ex7