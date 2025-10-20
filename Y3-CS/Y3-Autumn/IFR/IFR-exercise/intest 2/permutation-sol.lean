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

lemma insert_snoc : ∀ a : A, ∀ as : list A,
              Insert a as (snoc as a) :=
begin
  assume a as,
  induction as with a' as' ih,
  dsimp [snoc],
  apply ins_hd,
  --
  apply ins_tl,
  exact ih,
end

theorem perm_rev : ∀ as : list A, Perm as (rev as) :=
begin
  assume as,
  induction as with a as' ih,
  dsimp [rev],
  apply perm_nil,
  --
  apply perm_cons,
  exact ih,
  dsimp [rev],
  -- we need to prove some lemma
  -- Insert a as (snoc as a)
  apply insert_snoc,
end


lemma perm_aux : ∀ a : A, ∀ bs cs : list A, 
          Insert a bs cs → ∀ as : list A, (Perm bs as → Perm cs (a :: as)) :=
begin
  assume a bs cs h1,
  induction h1,
  -- Insert a bs cs
  case ins_hd : n ns { -- Insert a bs (a::bs), i.e., cs = a :: bs
    assume as h2,
    apply perm_cons,
    exact h2,
    apply ins_hd,
  },
  -- ∀ a b:A, ∀ as as': list A, Insert a as as' → Insert a (b :: as) (b :: as')
  case ins_tl : n m ns ms h3 ih{
    assume as h4,
    cases h4 with _ _ ls _ h5 h6 ih,
    apply perm_cons,
    apply ih,
    exact h5,
    apply ins_tl,
    exact h6,
  },
end


theorem rev_perm : ∀ as bs : list A, Perm as bs → Perm bs as := 
begin
  assume ns ms h1,
  induction h1,
  -- induction on Perm nil bs
  case perm_nil : {
    apply perm_nil,
  },
  case perm_cons : a ass bss bss' h2 h3 ih {
    -- Perm ass bss → Insert a bss bss' → Perm bss ass → Perm bss' (a :: ass)
    -- Perm as bs → Insert a bs cs → Perm cs (a::as)
    apply perm_aux,
    exact h3,
    exact ih,
  },
end



end ex7