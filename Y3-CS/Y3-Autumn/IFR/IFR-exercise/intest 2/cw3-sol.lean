/-
COMP2068-IFR Coursework 3 (100)
(Less or equal (≤))

The goal is to prove some properties of ≤ on the natural numbers:

a) ≤ is antisymmetric (50%)
b) ≤ is decidable (50%)

-/
set_option pp.structure_projections false

namespace cw3
 
open nat

/- from the lecture: 
  defn of + and proof that it is a commutative monoid.
-/

def add : ℕ → ℕ → ℕ 
| n zero := n
| n (succ m) := succ (add n m)

local notation (name := add) m + n := add m n
/-
If you get an error update your lean or use:
local notation m + n := add m n 
-/

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

-- from the lecture : definition of ≤ .

def le(m n : ℕ) : Prop :=
  ∃ k : ℕ , k + m = n

local notation (name := le) x ≤ y := le x y
/-
If you get an error update your lean or use:
local notation x ≤ y := le x y 
-/

-- end of lecture material

/- --- Do not add/change anything above this line (except the `local notation` syntax, if necessary) --- -/


/- a) Prove that ≤ is antisymmetric. (50%)
  Hint: it may be a good idea to prove some lemmas.-/

lemma plus0 : ∀ x y: ℕ, x + y = y → x = 0 :=
begin
  assume x y h1,
  induction y with y' h2,
  apply h1,
  apply h2,
  injection h1,
end 

lemma x_zero : ∀ x y: ℕ, x + y = 0 →  x = 0:=
begin
  assume x y xy,
  cases y with y' ih,
  exact xy,
  contradiction,
end

theorem anti_sym : ∀ x y : ℕ , x ≤ y → y ≤ x → x = y :=
begin
  dsimp [le],
  assume x y xy yx,
  cases xy with a axy,
  cases yx with b byx,
  have h : a + b = 0, 
  rewrite <- byx at axy,
  rewrite <- assoc at axy,
  apply plus0,
  apply axy,
  rewrite <- axy,
  have h': a = 0,
  apply x_zero,
  exact h,
  rewrite h',
  rewrite lneutr,
end


/-
b) Show that ≤ is decidable, by defining a function returning 
a boolean (10%).

You should define leb in such a way that you can prove
  ∀ m n : ℕ, m ≤ n ↔ leb m n = tt
-/

def leb : ℕ → ℕ → bool
| zero m := tt
| (succ m) zero := ff
| (succ m) (succ n) := leb m n

/- Now, show that leb computes ≤, i.e. that
 your definition of leb satisfies its specification. (40%) -/

lemma lezero : ∀ n : ℕ, ¬ ((succ n) ≤ zero) :=
begin
  assume n h,
  dsimp [le] at h,
  cases h with k h',
  contradiction,
end 


lemma leb_compl : ∀ m n : ℕ, m ≤ n →  leb m n = tt :=
begin
  dsimp[le],
  assume m,
  induction m with m' ih,
  assume n h,
  refl,
  --
  assume n h1,
  cases h1 with k' h1,
  rewrite <- h1,
  dsimp[add,leb],
  apply ih,
  existsi k',
  refl,
end


lemma lebsucc : ∀ m n : ℕ, m ≤ n → succ m ≤ succ n :=
begin
  assume m n mn,
  cases mn with k mn,
  existsi k,
  apply congr_arg succ,
  assumption,
end


lemma leb_sound : ∀ m n : ℕ, leb m n = tt → m ≤ n :=
begin
  assume m,
  induction m with m' ih,
  assume n h,
  existsi n,
  refl,
  --
  assume n h,
  cases n with n',
  dsimp [leb] at h,
  cases h,
  -- we need to show that m ≤ n → succ m ≤ succ n  
  apply lebsucc,
  apply ih,
  dsimp [leb] at h,
  assumption,
end

theorem leb_ok : 
  ∀ m n : ℕ, m ≤ n ↔ leb m n = tt :=
begin
  assume m n,
  constructor,
  apply leb_compl,
  apply leb_sound,
end



/- --- Do not add/change anything below this line --- -/
end cw3