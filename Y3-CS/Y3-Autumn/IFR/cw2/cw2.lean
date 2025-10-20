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
  cases h with y hy,
  assume x,
  existsi y,
  apply hy,
end

theorem q02 : ¬ ¬ (∀ x : A, PP x) → ∀ x : A, ¬ ¬ PP x :=
begin
  assume h,
  assume x,
  assume h1,
  apply h,
  assume h2,
  apply h1,
  apply h2,
end

theorem q03 : (∀ x : A, ¬ ¬ PP x) → ¬ ¬ ∀ x : A, PP x :=
begin
  assume h,
  assume h1,
  apply h1,
  assume a,
  cases em (PP a) with ppa nppa,
  exact ppa,
  have f : false,
  apply h,
  exact nppa,
  cases f,
end

theorem q04 : ¬ ¬ (∃ x : A, PP x) → ∃ x : A, ¬ ¬ PP x :=
begin
  assume h,
  apply raa,
  assume h1,
  apply h,
  assume h2,
  cases h2 with a ppa,
  apply h1,
  existsi a,
  assume h3,
  apply h3,
  exact ppa,
end


theorem q05 : (∃ x : A, true) → (∃ x: A, PP x → ∀ x : A, PP x) :=
begin
  assume h,  
  cases h with a ha, 
  cases em (∀ x : A, PP x) with p np,  
  existsi a, 
  assume h1,  
  exact p, 
  have h2 : ∃ x : A, ¬ PP x, 
  apply raa, 
  assume npp, 
  apply np, 
  assume b, 
  apply raa,
  assume ppb,
  apply npp,
  existsi b, 
  exact ppb,
  cases h2 with b nppb,  
  existsi b,  
  assume ppb,  
  have f : false,
  apply nppb,
  exact ppb,
  cases f,
end

theorem q06 : ¬ ¬ (∀ x : A, ∀ y : A, RR x y) 
      → ∀ x : A, ∀ y: A, ¬ ¬ RR x y :=
begin
  assume h,
  assume x,
  assume y,
  assume h1,
  apply h,
  assume h2,
  apply h1,
  apply h2,
end

theorem q07 : ¬ ¬ (∀ x : A, ∃ y : A, RR x y) 
      → ∀ x : A, ∃ y: A, ¬ ¬ RR x y :=
begin
    assume h,
    assume x,
    apply raa,
    assume h1,
    apply h,
    assume h2,
    apply h1,
    have h3 : ∃ y : A, RR x y, 
    apply h2,
    cases h3 with y hy,
    existsi y,
    assume nhy,
    apply h,
    have f : false,
    apply nhy,
    exact hy,
    cases f,
end

theorem q08 : (∀ x : A, ∃ y: A, ¬ ¬ RR x y) 
         → ¬ ¬ (∀ x : A, ∃ y : A, RR x y) :=
begin
   assume h,
   assume h1,
   have h2 : ¬ false,
   assume f,
   exact f,
   apply h1,
   assume x,
   have h3 : ∃ y : A, ¬ ¬ RR x y,
   apply h,
   cases h3 with y hy,
   existsi y,
   apply raa,
   exact hy,
end


theorem q09 : ¬ ¬ (∃ x : A, ∀ y : A, RR x y) 
      → ∃ x : A, ∀ y: A, ¬ ¬ RR x y :=
begin
    assume h,
    apply raa,
    assume h1,
    apply h,
    assume h2,
    apply h1,
    cases h2 with x hx,
    existsi x,
    assume y,
    assume h3,
    apply h,
    assume h4,
    apply h3,
    apply hx,
end

theorem q10 : (∃ x : A, ∀ y: A, ¬ ¬ RR x y) 
         → ¬ ¬ (∃ x : A, ∀ y : A, RR x y) :=
begin
  assume h,
  assume h1,
  apply h1,
  cases h with x hx,
  existsi x,
  assume y,
  apply raa,
  apply hx,
end

/- --- Do not add/change anything below this line --- -/
end proofs