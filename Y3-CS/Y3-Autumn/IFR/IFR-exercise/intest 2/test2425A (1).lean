/-
  IFR Class Test 2024-2025

  - There are totally 8 questions  
  - Please complete all the questions and then submit your lean file to Moodle.
  - You can only use the tactics that are introduced in the module.
  - The tactics can only be used in the way that they have been introduced in 
    this module. For example, you can only use 'contradiction' to resolve
    premises such as 'tt = ff'.
  - Note, you cannot use classical logic except for the question Q4.
  - You are not allowed to import any additional library (including mathlib)
  - All the original theorems and definitions in these file can be directly used.
  - You are allowed to use auxiliary lemmas to help with the proof. 
    However, those lemmas must be proved first.

-/

set_option pp.structure_projections false
namespace test
open nat
open list

variables P Q R : Prop
variables A B C : Type
variable {D : Type}
variables PP QQ: A → Prop

/-
  The following definition and theorems are from the lectures. You can use
  them directly if necessary.
-/

-- Boolean: For Q5
def bor : bool → bool → bool 
| tt b := tt
| ff b := b
local notation (name := bor) x || y := bor x y

def is_tt : bool → Prop 
| tt := true
| ff := false


-- Natural Number: For Q6 and Q7
def add : ℕ → ℕ → ℕ 
| n zero := n
| n (succ m) := succ (add n m)
local notation (name := add) m + n := add m n

theorem lneutr : ∀ n : ℕ, 0 + n = n :=
begin
 assume n,
 induction n with n' ih,
 reflexivity,
 dsimp [(+)],
 rewrite ih,
end

theorem rneutr : ∀ n : ℕ, n + 0 = n :=
begin
  assume n,
  dsimp [(+),add],
  reflexivity
end

theorem assoc : ∀ l m n : ℕ, 
    (l + m) + n = l + (m + n) :=
begin
  assume l m n,
  induction n with n' ih,
  dsimp [(+),add],
  reflexivity,
  dsimp [(+),add],
  rewrite ih,
end   

lemma add_succ : ∀ m n : ℕ,
  (succ m) + n = succ (m + n) :=
begin
  assume m n,
  induction n with n' ih,
  dsimp [(+),add],
  reflexivity,
  dsimp [(+),add],
  rewrite ih,
end

theorem comm : ∀ m n : ℕ , m + n = n + m :=
begin
  assume m n,
  induction n with n' ih,
  dsimp [(+),add],
  rewrite lneutr,
  dsimp [(+),add],
  rewrite add_succ,
  rewrite ih,
end
-- less than or equal
def le(m n : ℕ) : Prop :=
  ∃ k : ℕ , k + m = n
local notation (name := le) x ≤ y := le x y
-- less than
def lt(m n : ℕ) : Prop :=
    m + 1 ≤ n 
local notation (name := lt) x < y := lt x y


-- Less than on bool: For Q8
def leb : ℕ → ℕ → bool 
| 0 n := tt
| (succ m) 0 := ff
| (succ m) (succ n) := leb m n

-- If-then-else: For Q8
def ite : bool → D → D → D
| tt a b := a
| ff a b := b

local notation (name := ite) 
  if x then a else b := ite x a b

/- --- Do not add/change anything above this line --- -/


-- Q1: Proposition Logic (10%)
theorem q01 : (P → P → Q) ↔  (P → Q) := 
begin
  constructor,
  assume h,
  assume p,
  apply h,
  exact p,
  exact p,
  assume h,
  assume p,
  exact h,
end

-- Q2: Proposition Logic (10%)
theorem q02 : (P → Q ∧ R) ↔ (P → Q) ∧ (P → R) := 
begin
  constructor,
  assume h,
  constructor,
  assume p,
  have h1 : Q ∧ R,
  apply h,
  exact p,
  cases h1 with q r,
  exact q,
  assume p,
  have h1 : Q ∧ R,
  apply h,
  exact p,
  cases h1 with q r,
  exact r,
  assume h,
  cases h with h1 h2,
  assume p,
  constructor,
  apply h1,
  exact p,
  apply h2,
  exact p,
end

-- Q3: Predicate Logic (10%)
theorem q03 : ∀ x y z : A, x = y → x ≠ z → y ≠ z := 
begin
  assume x y z,
  assume h,
  assume h1,
  cases h with a ha,
  exact h1,
end

open classical
-- Q4: Predicate Logic (10%)
-- You can use classical logic for this question
theorem q04 :  ∀ x y z : A, x ≠ y → (x ≠ z ∨ y ≠ z) := 
begin
  assume x y z,
  assume h,
  cases em (x ≠ z) with h1 h2,
  cases em (y ≠ z) with h3 h4,
  left,
  exact h1,
  left,
  apply h1,
  
end

-- Q5: Boolean  (15%)
theorem q05 : ∀ x y : bool, is_tt x ∨ is_tt y ↔ is_tt (x || y) :=
begin
  assume x y,
  constructor,
  assume h,
  cases x,
  cases h with h1 hr,
  cases h1,
  exact hr,
  cases h with h1 hr,
  exact h1,
  constructor,
  assume h,
  cases x,
  right,
  exact h,
  left,
  constructor,
end

-- Q6: Natural Number, Less Than (15%)
theorem q06 : ∀ m n : ℕ, m ≤ n → m ≠ n → m < n := 
begin
  assume m n,
  assume h1,
  assume h2,
  dsimp [le] at h1,
  cases h1 with a ha,
  have h3 : a+m = n,
  exact ha,
end 

-- Q7: Natural Number, Less Than (15%)
theorem q07 : ∀ m n : ℕ, m < n ∨ m = n → m ≤ n := 
begin
  assume m n,
  assume h,
  dsimp[lt,le] at h,
  dsimp[le],
  cases h,
  cases h with k h1,
  existsi k+1,
  rw assoc,
  rw comm 1 m,
  exact h1,
  existsi 0,
  rw lneutr,
  exact h,
end

-- Q8: Complete the definition of Bubble Sort (15%)
def bubble : list ℕ → list ℕ := 
begin
  assume h,
  exact h,
end

end test