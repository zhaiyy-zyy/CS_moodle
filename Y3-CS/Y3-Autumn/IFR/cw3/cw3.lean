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

lemma l1 : ∀ x y : ℕ, x + y = x → y = 0 :=
begin
  assume x y,
  induction x with x' ih,
  rw lneutr,
  assume h,
  exact h,
  rw add_succ,
  assume h1,
  apply ih,
  injection h1,
end

theorem anti_sym : ∀ x y : ℕ , x ≤ y → y ≤ x → x = y :=
begin
  assume x y,
  dsimp[le],
  assume h1 h2,
  cases h1 with k1 hk1,
  cases h2 with k2 hk2,
  rw ← hk1,
  rw ← hk2 at hk1,
  rw ← assoc at hk1,
  have h3 : k1 + k2 = 0,
  rw comm at hk1,
  apply l1,
  exact hk1,
  have h4 : k2 = 0,
  cases k2,
  refl,
  contradiction,
  rw h4 at h3,
  rw rneutr at h3,
  rw h3,
  rw lneutr,
end

/-
b) Show that ≤ is decidable, by defining a function returning 
a boolean (10%).

You should define leb in such a way that you can prove
  ∀ m n : ℕ, m ≤ n ↔ leb m n = tt
-/

def leb : ℕ → ℕ → bool 
| zero n := tt
| (succ m) zero := ff 
| (succ m) (succ n) := leb m n

/- Now, show that leb computes ≤, i.e. that
 your definition of leb satisfies its specification. (40%) -/

lemma l2 : ∀ m n : ℕ, m ≤ n → succ m ≤ succ n :=
begin
  assume m n h,
  cases h with k hk,
  dsimp[le],
  existsi k,
  rw ← hk,
  refl,
end

lemma l3: ∀ m n : ℕ, m ≤ n → leb m n = tt :=
begin
  assume m,
  induction m with m' ih,
  assume n h,
  dsimp[leb],
  refl,
  assume n h,
  cases n,
  cases h with k hk,
  contradiction,
  dsimp[leb],
  apply ih,
  dsimp[le] at h,
  cases h with k hk,
  dsimp[le],
  existsi k,
  injection hk,
end

lemma l4 : ∀ m n : ℕ, leb m n = tt → m ≤ n :=
begin
  assume m,
  induction m with m' ih,
  assume n h,
  dsimp[le],
  existsi n,
  refl,
  assume n h,
  cases n,
  dsimp [leb] at h,
  cases h,
  dsimp [leb] at h,
  apply l2,
  apply ih,
  exact h,
end

theorem leb_ok : 
  ∀ m n : ℕ, m ≤ n ↔ leb m n = tt :=
begin
  assume m n ,
  constructor,
  apply l3,
  apply l4,
end


/- --- Do not add/change anything below this line --- -/
end cw3