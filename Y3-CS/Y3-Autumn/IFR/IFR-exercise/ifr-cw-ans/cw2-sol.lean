/-
COMP2068-IFR 
Coursework 02 (10 points)

Prove all the following propositions in Lean. 1 point per exercise.
That is replace "sorry" with your proof. 

If a proposition is provable intuitionistically, then prove it intuitionistically.

If a proposition is not provable intuitionistically, but provable classically, then prove it classically. 

You are only allowed to use the tactics introduced in the lecture
  (e.g. assume, exact, apply, constructor, cases, left, right, have,
  existsi)

  Please only use the tactics in the way indicated in the lecture notes,
  otherwise we may deduct points.
-/
namespace proofs

variables P Q R : Prop
variables A B C : Type
variables PP QQ: A → Prop
variables RR : A → A → Prop

open classical

theorem raa : ¬ ¬ P → P := 
begin
  assume nnp,
  cases (em P) with p np,
  exact p,
  have f : false,
  apply nnp,
  exact np,
  cases f,
end

/- --- Do not add/change anything above this line --- -/


theorem q01 : (∃ y : A, ∀ x : A, RR x y) 
              → (∀ x:A, ∃ y : A , RR x y) :=
begin
  assume h1 a,
  cases h1 with b h1,
  existsi b,
  apply h1, 
end

/-
¬¬ ∀ P → ∀ ¬¬P
¬¬ ∃ P → ∃ ¬¬P
¬¬ ∀ ∀ P → ∀ ∀ ¬¬P
¬¬ ∀ x ∃ y P → ∀ ∃ ¬¬P    a f(a)
¬¬ ∃ x ∀ y P → ∃ ∃ ¬¬P


P → ¬¬ PP

-/


theorem q02 : ¬ ¬ (∀ x : A, PP x) → ∀ x : A, ¬ ¬ PP x :=
begin
  assume h1,
  assume a,
  assume nppa,
  apply h1,
  assume h2,
  apply nppa,
  apply h2,
end

theorem q03 : (∀ x : A, ¬ ¬ PP x) → ¬ ¬ ∀ x : A, PP x :=
begin
  assume h1 h2,
  apply h2,
  assume a,
  have h' : ¬¬ PP a,
  apply h1,
  apply raa,
  exact h',
end

theorem q04 : ¬ ¬ (∃ x : A, PP x) → ∃ x : A, ¬ ¬ PP x :=
begin
  assume h1,
  have h' : (∃ x : A, PP x),
  apply raa,
  exact h1,
  cases h' with a h',
  existsi a,
  assume nppa,
  apply nppa,
  exact h',
end


theorem q05 : (∃ x : A, true) → 
  (∃ x: A, PP x → ∀ y : A,PP y) :=
begin
-- case 1: true → true
-- case 2: false → false
assume h1,
cases (em (∀ y : A, PP y)),
cases h1 with a h1,
existsi a,
assume ppa,
exact h,
--
have h' : (∃ y : A, ¬ PP y),
apply raa,
assume h2,
apply h,
assume a,
apply raa,
assume nppa,
apply h2,
existsi a,
exact nppa,
cases h' with b h',
existsi b,
assume ppb,
have x : false,
apply h',
exact ppb,
cases x,
end


theorem q06 : ¬ ¬ (∀ x : A, ∀ y : A, RR x y) 
      → ∀ x : A, ∀ y: A, ¬ ¬ RR x y :=
begin
  assume h1,
  assume a b,
  assume nppa,
  apply h1,
  assume h2,
  apply nppa,
  apply h2,
end


theorem q07 : ¬ ¬ (∀ x : A, ∃ y : A, RR x y) 
      → ∀ x : A, ∃ y: A, ¬ ¬ RR x y :=
begin
  assume h1 a, 
  have h' : ∀ (x : A), ∃ (y : A), RR x y,
  apply raa (∀ (x : A), ∃ (y : A), RR x y),
  exact h1,
  have h'' : ∃ (y : A), RR a y,
  apply h',
  cases h'' with b h'',
  existsi b,
  assume nrab,
  apply nrab,
  exact h'',
end

theorem q08 : (∀ x : A, ∃ y: A, ¬ ¬ RR x y) 
         → ¬ ¬ (∀ x : A, ∃ y : A, RR x y) :=
begin
  assume h1 h2,
  apply h2,
  assume a,
  have h' : ∃ (y : A), ¬¬RR a y,
  apply h1,
  cases h' with b h',
  existsi b,
  apply raa,
  exact h',
end



theorem q09 : ¬ ¬ (∃ x : A, ∀ y : A, RR x y) 
      → ∃ x : A, ∀ y: A, ¬ ¬ RR x y :=
begin
  assume h1,
  have h' : ∃ (x : A), ∀ (y : A), RR x y,
  apply raa,
  exact h1,
  cases h' with a h',
  existsi a,
  assume b,
  assume nrrab,
  apply nrrab,
  apply h',
end

theorem q10 : (∃ x : A, ∀ y: A, ¬ ¬ RR x y) 
         → ¬ ¬ (∃ x : A, ∀ y : A, RR x y) :=
begin
  assume h1 h2,
  apply h2,
  cases h1 with a h1,
  existsi a,
  assume b,
  have h' : ¬¬ RR a b,
  apply h1,
  apply raa,
  exact h',
end

/- --- Do not add/change anything below this line --- -/
end proofs