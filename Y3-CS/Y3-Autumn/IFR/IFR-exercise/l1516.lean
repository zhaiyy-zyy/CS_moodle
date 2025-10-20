/-
Lecture 15&16 : Lists，Reverse
-/

namespace l1516
set_option pp.structure_projections false

-- Examples of lists

-- Definition of list

-- Comparision with ℕ 

/- 
Some basic properties for ℕ:
1) zero ≠ succ n
2) succ is injective: succ m = succ n → m = n  
-/

open list
-- here we use {} to make the types implicit, 
-- e.g., list A means a list of A, where A is a implicit type here
variables {A B C: Type} 

/-
  Now, we try to find out if a list has the similar properties
  1) nil ≠ cons
  2) cons is injective
-/

-- 1) nil ≠ cons
--nil表示空列表；cons a as表示由元素 a 和列表 as 组成的新列表，是一个非空列表。
example : ∀ a : A, ∀ as : list A, nil ≠ cons a as := 
begin
  assume a as h,--引入a as h变量
  cases h,
end

/-
  2) cons is injective: 
-/
example : ∀ a b : A, ∀ as bs : 
      list A, a::as = b::bs → a = b ∧ as = bs :=
begin
  assume a b as bs h,
  constructor,
  injection h,
  injection h,
end


/-
  Can we prove it without using injection?
-/
example : ∀ a b : A, ∀ as bs : 
      list A, a::as = b::bs → a = b :=
begin
  assume a b as bs h,
  cases h,
  refl,
end


-- Exercise: prove the following without using injection
-- Think about what function f is required 
example : ∀ a b : A, ∀ as bs : 
      list A, a::as = b::bs → as = bs :=
begin
  assume a b as bs h,
  cases h,
  refl,
end

/-
  Append (++):
  [1,2,3] ++ [4,5,6] = [1,2,3,4,5,6]
-/

-- Definition of append
--两个列表合并
def append : list A → list A → list A 
| [] as := as
| (a::as) bs := a :: append as bs

-- sanity check

-- Can we have recusion on the 2nd parameter?


open nat

/-
  Length: the number of elements in a list
-/

-- definition of length
--列表长度:a 为头，as为尾 length=succ（as）；例length[1,2,3]=3
def length : list A → ℕ 
| []      := 0
| (a::as) := succ(length as)

#eval length [1,2,3]



-- Excercise:
theorem add_lneutr : ∀ n : ℕ, 0 + n = n :=
begin
    assume n,
    induction n with n' ih,
    refl,
    apply congr_arg succ,
    exact ih,
end

example : ∀ as bs : list A,
  length (as ++ bs) = length as + length bs := 
begin 
  assume as bs,
  induction as with a as' ih,
  dsimp[(++)],
  dsimp[length],
  rw add_lneutr,
  dsimp[length],
  rw ih,
  rw succ_add,
end


/-
  Monoid: a set S and a binary operator f : S → S → S
    1) exisits a neutral element x such that
        ∀ s : S, f x s = s = f s x
    2) f is associative

  Is there a Monoid for list? what is f?
-/

-- [] is left neutral, this one is easy as list is defined this way
theorem lneutr : ∀ as : list A, [] ++ as = as :=
begin
  assume as,
  dsimp[(+)],
  refl,
end

-- [] is right neutral
theorem rneutr : ∀ as : list A, as ++ [] = as :=
begin
  assume as,
  induction as with a as' ih,
  refl,
  dsimp [(++)],
  rewrite ih,
end

-- ++ is associative
theorem assoc : ∀ as bs cs : list A,
    (as ++ bs) ++ cs = as ++ (bs ++ cs) :=
begin
  assume as bs cs,
  induction as with a as' ih,
  dsimp[(+)],
  refl,
  dsimp[(+)],
  rw ih,
end

-- ++ is not commutative, e.g., [0] ++ [1] ≠ [1] ++ [0]
-- can we prove this?
theorem comm : ¬ (∀ as bs : list A, as ++ bs = bs ++ as) :=
begin
    assume h,
    have x : [0] ++ [1] == [1] ++ [0],
    dsimp[(++)],
    contradiction,
end

theorem comm2 : ¬ (∀ A : Type, 
  ∀ as bs : list A,
  as ++ bs = bs ++ as) :=
begin
  assume h,
  have h1 : [0] ++ [1] = [1] ++ [0],
  apply h,
  dsimp[(++)] at h1,
  have h2 : 0=1,
  injection h1,
  cases h2,
end

/-
  Reverse:

  What do we mean by reverse a list?
  rev : list A → list A

  How to achieve it?
-/

def snoc : list A → A → list A := sorry

-- definition of rev
def rev : list A → list A := sorry

-- sanity check


/-
  We want to show that, the reverse of the reverse is itself
-/
--theorem revrev : 
--  ∀ as : list A, rev (rev as) = as := sorry

/-
  Functor: a structure which allows us to apply functions without changing 
  the general structure
-/

--def map_list : (A → B) → list A → list B := sorry

/-
two basic ingredients
1) identity
id : A → A

2) composition
comp : (B → C) → (A → B) → A → C
-/
-- def id : A → A := sorry

-- def comp : (B → C) → (A → B) → A → C := sorry
-- local notation (name := comp) f ∘ g := comp f g

/-
Simple properties 
  1) id ∘ f = f
    ∀ f : A → B, id ∘ f = f 

  2) f ∘ id = f 
    ∀ f : A → B, f ∘ id = f

  3) (f ∘ g) ∘ h = f ∘ (g ∘ h)
    ∀ h : C → D, ∀ g : B → C, ∀ f : A → B, (h ∘ g) ∘ f = h ∘ (g ∘ f)
  
is this a monoid? why not
it is a category
monoind: one object categories.

However, this isn’t really a monoid because there isn’t one fixed type 
on which the operations are defined but a whole family of types. 
This structure is called a category, which is the basic notion in the 
field of category theory.
-/

/-
To make list a functor, we need to show two properties:
map_list id = id
  Given a list, if we apply id on each single element in this list, the 
  resulting list should be exactly the same as the original one
map_list (g ∘ f) = map_list g ∘ map_list f
  Apply the composition of two functions g and f on each element of the given
  list, should be equivalent to do the mapping first and then the composition
 -/


/-
How to prove two functions are equal?
f g : A → B
if two function f g are equal, then for all possible a : A, 
the output should be the same
∀ a : A, f a = g a → f = g
funext
-/

-- map_list id = id 

/-
I am writing @id A instead of just id because I need to tell lean 
that I want to use the type variable A here, so I have to give 
it explicitly. @id is id with the implicit parameter A : Type 
made explicit.
-/
--theorem map_id : map_list (@id A) = id := sorry

end l1516