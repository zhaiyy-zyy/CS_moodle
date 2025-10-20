/-
COMP2068-IFR Extra Exercise
(Less than <)

The goal is to prove some properties of < on the natural numbers:

-/
set_option pp.structure_projections false

namespace ee1
 
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

-- from the lecture : definition of < .
def lt(m n : ℕ ) : Prop :=
  m + 1 ≤ n

local notation (name := lt) x < y := lt x y

/-
If you get an error update your lean or use:
local notation x ≤ y := le x y 
-/

-- end of lecture material




/- --- Do not add/change anything above this line (except the `local notation` syntax, if necessary) --- -/

/- Prove the following properties of '<' -/


lemma zeroplus : ∀ m n : ℕ,
        m + n = n → m = 0 :=
begin
  assume m n h,
  induction n with n' ih,
  exact h,
  apply ih,
  dsimp [add] at h,
  injection h,
end


-- < is not reflexitive, i.e., ∀ n :ℕ, ¬ (n < n)
theorem lt_refl : ∀ n : ℕ, ¬ (n < n) :=
begin
  assume n h,
  dsimp [lt, le] at h,
  cases h with k h,
  have x : k + 1 = 0,
  rewrite comm n at h,
  rewrite <- assoc at h,
  --
  apply zeroplus,
  exact h,
  cases x, 
end


-- < is transitive
-- ∀ l m n : ℕ, l < m → m < n → l < n 
theorem lt_trans : ∀ l m n : ℕ, 
  l < m → m < n → l < n :=
begin
  assume l m n lm mn,
  cases lm with k1 lm,
  cases mn with k2 mn,
  -- some k3 which makes k3 + (l + 1) = n
  -- k3 = k1 + k2 + 1
  existsi k2 + 1 + k1,
  rewrite assoc (k2+1) k1,
  rewrite lm,
  rewrite assoc,
  rewrite comm 1,
  exact mn,
end 


-- < is asymmetry 
-- ∀ m n : ℕ, m < n →  n < m → false
theorem lt_asym : ∀  m n : ℕ, 
  m < n → n < m → false := 
begin
  assume m n mn nm,
  cases mn with k1 mn,
  cases nm with k2 nm,
  rewrite <- mn at nm,
  -- ... + m = m 
  rewrite assoc k1 at nm,
  rewrite comm (m+1) at nm,
  rewrite comm m at nm,
  rewrite <- assoc at nm,
  rewrite <- assoc 1 at nm,
  rewrite <- assoc (k2+k1) at nm,
  have x : k2 + k1 + 2 = 0,
  apply zeroplus,
  exact nm,
  cases x,
end


-- prove < is trichotomy
theorem trich : ∀ m n : ℕ, 
  m < n ∨ m = n ∨ n < m :=
begin
  assume m n,
  induction m with m' ih,
  cases n,
  -- 0<0 ∨ (0 = 0 ∨ 0<0)
  right,
  left,
  refl,
  --
  left,
  existsi n,
  refl,
  --
  cases n,
  right,
  right,
  existsi m',
  refl,
  cases ih with h1 h2,
  cases h1 with k1 h1,
  -- k1+(m'+1) = succ n
  -- m' ≤ n
  cases k1,
  right,
  left,
  rewrite lneutr at h1,
  exact h1,
  -- k ≠ 0, m' < n
  left,
  existsi k1,
  rewrite <- h1,
  rewrite add_succ,
  rewrite add_succ,
  dsimp [add],
  refl,
  cases h2 with h3 h4,
  right,
  right,
  rewrite <- h3,
  existsi 0,
  rewrite lneutr,
  refl,
  right,
  right,
  cases h4 with k1 h4,
  -- k + succ n + 1 = succ m'
  -- k1 +1 + succ n + 1 = m' +1
  existsi k1 + 1,
  rewrite comm k1,
  rewrite assoc 1,
  rewrite h4,
  rewrite comm,
  refl,
end


/- --- Do not add/change anything below this line --- -/
end ee1