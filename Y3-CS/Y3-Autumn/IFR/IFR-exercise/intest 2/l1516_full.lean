/-
Lecture 15&16 : Lists，Reverse
-/

namespace l1516
set_option pp.structure_projections false


-- example of lists in Lean 3
#check [1,2,3] -- list of natural numbers
#check [tt,ff] -- list of boolean
#check [[0,1],[2],[]] -- a list of list

-- #check [0,tt]

#check []  -- nil
#check 1 :: [2,3] -- cons
-- Haskell : -> Lean ::
-- Haskell :: -> Lean :
#check 1 :: 2 :: 3 :: []

/-
inductive list (A: Type) : Type
| nil  : list
| cons : A → list → list
-/

-- recall the inductive definition of natural number
/-
inductive ℕ : Type
| zero   : ℕ 
| succ   : ℕ → ℕ  

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
example : ∀ a : A, ∀ as : list A, nil ≠ cons a as := 
begin
  assume a as h,
  -- how to prove false? try to find the flying pig (false) in the premise
  -- nil and cons a as are different based on the inductive definition
  cases h, -- we use cases here, as h is the flying pig
end 

/-
  2) cons is injective: what does that mean?
  Given the fact that a :: as = b :: bs, what can we have?
  a = b ∧ as = bs     both the heads and tails of these two lists are equivalent
-/
example : ∀ a b : A, ∀ as bs : 
      list A, a::as = b::bs → a = b ∧ as = bs :=
begin
  assume a b as bs h,
  -- how to prove conjunction? constructor
  constructor,
  -- we can use injection to prove it
  injection h,
  injection h,
end

/-
  We want to prove the above theorem but without using "injection"
  Let's prove two lemmas:
    a::as = b::bs → a = b 
    a::as = b::bs → as = bs

  Remember how we prove succ is injective without using injection
    1) We defined an inverse function "pred" for "succ" which returns 
      the predecessor of a number
    2) we then use pred(succ n) to represent n, and use rewrite to prove it

  We need something similar here
-/

def head : list A → A 
| []         := sorry -- can we use []?
| (a :: as)  := a -- as can be written as _ to indicate it works for any lists


example : ∀ a b : A, ∀ as bs : 
      list A, a::as = b::bs → a = b :=
begin
  assume a b as bs h,
  -- if we want to use h, then we need a function f
  -- by using which we could have f (a :: as) = a and f (b :: bs) = b
  -- our goal a = b then becomes f (a :: as) = f (b :: bs)
  -- what is this funciton f?
  change head (a::as) = head (b::bs), -- we change the goal by the definition of head
  rewrite h,
end


-- Exercise: prove the following without using injection
-- Think about what function f is required 
example : ∀ a b : A, ∀ as bs : 
      list A, a::as = b::bs → as = bs :=
begin
  sorry,
end

/-
  Append (++):
  [1,2,3] ++ [4,5,6] = [1,2,3,4,5,6]
-/

-- definition of append
def append : list A →  list A → list A
| [] as := as
| (a::as) bs := a :: (append as bs)

-- local notation as ++ bs := append as bs, if the following didn't work 
local notation (name := append) as ++ bs := append as bs

-- sanity check
#eval [1,2,3] ++ [4,5,6]

-- can we have recusion on the 2nd parameter?
def append' : list A →  list A → list A
| as []      := as
| as (b::bs) := b :: (append' as bs)

#eval (append' [1,2,3] [4,5,6])

open nat

/-
  Length: the number of elements in a list
-/

-- definition of length
def length : list A → ℕ 
| []      := 0
| (a::as) := succ(length as)

#eval length [1,2,3]


-- Excercise:
example : ∀ as bs : list A,
  length (as ++ bs) = length as + length bs := sorry


/-
  Monoid: a set S and a binary operator f : S → S → S
    1) exisits a neutral element x such that
        ∀ s : S, f x s = s = f s x
    2) f is associative

  Is there a Monoid for list? what is f?

    S : list
    x : []
    f : ++
-/

-- [] is left neutral, this one is easy as list is defined this way
theorem lneutr : ∀ as : list A, [] ++ as = as :=
begin
  assume as,
  dsimp [(++)],
  reflexivity,
end

-- [] is right neutral
theorem rneutr : ∀ as : list A, as ++ [] = as :=
begin
  assume as,
  -- we can use induction as a list is defined inductively, similar to ℕ
  -- induction on a list give you two cases: 
  --  1) when the list is empty 
  --  2) list is not empty with an iterative hypothesis
  induction as with a as' ih, -- three items can be renamed: the head, the tail and iterative hypothesis
  -- dsimp[(++)], this step can be ignored, as refl will automatically call dsimp
  reflexivity,
  dsimp [(++)],
  rewrite ih,
end

/-
example : ∀ as : list A, as ++ [] = as :=
begin
  assume as,
  induction as,
  case list.nil {
    refl,
  },
  case list.cons : a as' ih {
    dsimp [(++)],
    rewrite ih,
  },
end
-/

-- ++ is associative
theorem assoc : ∀ as bs cs : list A,
    (as ++ bs) ++ cs = as ++ (bs ++ cs) :=
begin
  assume as bs cs,
  -- why induction on as? go and check the definition of ++
  induction as with a as' ih,
  --dsimp [(++)], this step could be ignored
  reflexivity,
  /-
  ((a :: as')++bs)++cs
  = (a :: (as' ++ bs))++cs
  = a :: ((as' ++ bs) ++ cs)
  = a :: (as' ++ bs ++ cs)
  -/
  dsimp [(++)],
  rewrite ih,
end

-- ++ is not commutative, e.g., [0] ++ [1] ≠ [1] ++ [0]
-- can we prove this?
-- unit : Type
-- star : unit 
-- list unit = ℕ 
theorem comm : ¬ (∀ as bs : list A, as ++ bs = bs ++ as) :=
begin
    sorry,
end

theorem comm2 : ¬ (∀ A : Type, 
  ∀ as bs : list A,
  as ++ bs = bs ++ as) :=
begin
  assume h,
  -- how to prove false?
  -- how to prove h is incorrect?
  -- assume a counterexample is true 
  have hh : [0]++[1] = [1]++[0],
  apply h,
  dsimp [(++)] at hh,
  -- if we know that [0, 1] = [1, 0], then 0 must equal to 1
  have hhh : 0=1,
  -- cons is injective
  injection hh,
  cases hhh,
end

/-
  Reverse:
  rev : list A → list A
  rev [1,2,3] = [3,2,1]

  How to define it recursively?
  take the first element out and put it at the end
  rev [1,2,3] = (rev [2,3]) ++ [1]
  rev [3] ++ [2] ++ [1]
  rev [] ++ [3] ++ [2] ++ [1]

  We need a function to do something similar to cons but in the other direction
-/

def snoc : list A → A → list A
| [] a      := [a]
-- [a] ++ as ++ [b]
-- [a] ++ (as ++ [b]) we know ++ is associative
-- a :: (snoc as b)
| (a::as) b := a :: (snoc as b) 

-- definition of rev
def rev : list A → list A
| []      := []
| (a::as) := snoc (rev as) a 

-- sanity check
#eval (rev [1,2,3])
#eval (rev (rev [1,2,3]))



lemma revsnoc : ∀ a : A, ∀ as : list A, 
    rev (snoc as a) =  a :: (rev as) :=
begin
  assume a as,
  induction as with a' as' ih,
  -- rev (snoc nil a) = a :: rev nil
  -- rev ([a]) = a :: rev nil
  -- rev (a :: []) = a :: rev nil
  -- snoc (rev []) a = a :: []
  -- snoc [] a = [a]
  -- [a] = [a]
  dsimp [snoc, rev],
  refl,
  --
  --rev (snoc (a' :: as') a) = a :: rev (a' :: as')
  --rev (a' :: (snoc as' a)) = a :: rev (a' :: as')
  --snoc (rev (snoc as' a)) a' = a :: (snoc (rev as') a')
  dsimp [snoc, rev],
  rewrite ih,
  -- snoc (a :: rev as') a' = a :: (snoc (rev as') a')
  -- a :: (snoc (rev as') a') = a :: (snoc (rev as') a')
  dsimp [snoc],
  refl,
end

/-
  We want to show that, the reverse of the reverse is itself
-/
theorem revrev : 
  ∀ as : list A, rev (rev as) = as :=
begin
  assume as,
  induction as with a as' ih,
  refl,
  --
  dsimp [rev],
  -- rev (snoc (rev as') a) = a :: as'
  -- assume as = rev as'
  -- rev (snoc as a) =  a :: (rev as)
  -- we want to show that rev (snoc as a) = a :: (rev as)
  rewrite revsnoc,
  rewrite ih,
end


/-
  Functor: a structure which allows us to apply functions without changing 
  the general structure
-/

def map_list : (A → B) → list A → list B 
| f [] := []
| f (a :: as) := (f a) :: (map_list f as)

/-
two basic ingredients
1) identity
id : A → A

2) composition
comp : (B → C) → (A → B) → A → C
-/
def id : A → A 
| a := a

def comp : (B → C) → (A → B) → A → C
| g f a := g (f a)

local notation (name := comp) f ∘ g := comp f g

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
theorem map_id : map_list (@id A) = id :=
begin
  apply funext,
  assume as,
  induction as with a as' ih,
  dsimp [map_list, id],
  refl,
  --
  -- map_list id (a :: as') = a :: as'
  -- (id a) :: (map_list id as') = a :: as'
  -- a :: (map_list id as') = as :: as'
  dsimp [map_list, id],
  rewrite ih,
  dsimp [id],
  refl,
end



end l1516