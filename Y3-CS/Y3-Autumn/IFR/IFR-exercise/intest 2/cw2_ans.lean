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
  assume h,
  cases h with a ha,
  assume b,
  existsi a,
  apply ha,
end

theorem q02 : ¬ ¬ (∀ x : A, PP x) → ∀ x : A, ¬ ¬ PP x :=
begin
  assume h1,
  assume a,
  assume h2,
  apply h1,
  assume h3,
  apply h2,
  apply h3,
end

theorem q03 : (∀ x : A, ¬ ¬ PP x) → ¬ ¬ ∀ x : A, PP x :=
begin
  assume h1,
  assume h2,
  apply h2,
  assume a,
  apply raa,
  apply h1,
end

theorem q04 : ¬ ¬ (∃ x : A, PP x) → ∃ x : A, ¬ ¬ PP x :=
begin
  assume h1,
  apply raa,
  assume h2,
  apply h1,
  assume h3,
  cases h3 with a ppa,
  apply h2,
  existsi a,
  assume h4,
  apply h4,
  exact ppa,
end

theorem dm2_pred : ¬ (∀ x: A, PP x) -> ∃ x: A, ¬ PP x :=
begin
  assume h,
  apply raa,
  assume h1,
  apply h,
  assume a,
  apply raa,
  assume h2,
  apply h1,
  existsi a,
  exact h2,
end

theorem q05 : (∃ x : A, true) → (∃ x: A, PP x → ∀ x : A,PP x) :=
begin
  assume h,
  cases em (∀x: A, PP x) with h1 h2,
  cases h with a ha,
  existsi a,
  assume pa,
  exact h1,
  have h3: ∃ x: A, ¬ PP x,
  apply dm2_pred,
  exact h2,
  cases h3 with b hb,
  existsi b,
  assume h4,
  have f: false,
  apply hb,
  exact h4,
  cases f,
end

theorem q06 : ¬ ¬ (∀ x : A, ∀ y : A, RR x y) 
      → ∀ x : A, ∀ y: A, ¬ ¬ RR x y :=
begin
  assume h,
  assume a b,
  assume h1,
  apply h,
  assume h2,
  apply h1,
  apply h2,
end



theorem q07 : ¬ ¬ (∀ x : A, ∃ y : A, RR x y) 
      → ∀ x : A, ∃ y: A, ¬ ¬ RR x y :=
begin
    assume h1,
    assume a,
    apply raa,
    assume h2,
    apply h1,
    assume h3,
    apply h2,
    have h4: ∃ y : A, RR a y,
    apply h3,
    cases h4 with b hb,
    existsi b,
    assume h5,
    apply h5,
    exact hb,
end

theorem q08 : (∀ x : A, ∃ y: A, ¬ ¬ RR x y) 
         → ¬ ¬ (∀ x : A, ∃ y : A, RR x y) :=
begin
   assume h1,
   assume h2,
   apply h2,
   assume a,
   have h3: ∃ y : A, ¬ ¬ RR a y,
   apply h1,
   cases h3 with b hb,
   existsi b,
   apply raa,
   exact hb,
end


theorem q09 : ¬ ¬ (∃ x : A, ∀ y : A, RR x y) 
      → ∃ x : A, ∀ y: A, ¬ ¬ RR x y :=
begin
    assume h1,
    apply raa,
    assume h2,
    apply h1,
    assume h3,
    cases h3 with a ha,
    apply h2,
    existsi a,
    assume b,
    assume h4,
    apply h4,
    apply ha,
end

theorem q10 : (∃ x : A, ∀ y: A, ¬ ¬ RR x y) 
         → ¬ ¬ (∃ x : A, ∀ y : A, RR x y) :=
begin
  assume h1,
  assume h2,
  apply h2,
  cases h1 with a ha,
  existsi a,
  assume b,
  apply raa,
  apply ha,
end

/- --- Do not add/change anything below this line --- -/
end proofs