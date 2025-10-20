/-
COMP2068 IFR 
Exercise 02

Prove all the following propositions in Lean.
This is to replace "sorry" with your proof. 

You are only allowed to use the tactics introduced in the lecture.
You may use laws in classical logic if a statement is not provable in intuitionistic logic. 
-/



namespace proofs

variables P Q R : Prop

/- ---Do not add/change anything above this line --- -/

theorem q01: P → (P → Q) → P ∧ Q :=
begin
  assume p,
  assume pq,
  constructor,
  exact p,
  apply pq,
  exact p,
end

theorem q02: P ∨ Q → (P → Q) → Q :=
begin 
  assume h,
  assume h',
  cases h with p q,
  apply h',
  exact p,
  exact q,
end

open classical 

theorem q03: (P → Q)→ ¬ P ∨ Q :=
begin
  assume h,
  cases em P with p np,
  right,
  apply h,
  exact p,
  left,
  exact np,
end

theorem q04: (¬ P ∨ Q) → P → Q :=
begin
  assume h,
  assume p,
  cases h with h1 h2,
  have f: false,
  apply h1,
  exact p,
  cases f,
  exact h2,
end

theorem q05: ¬ P ↔ ¬ ¬ ¬ P :=
begin
  constructor,
  assume h,
  assume nnp,
  apply nnp,
  exact h,
  assume h,
  assume p,
  apply h,
  assume np,
  apply np,
  exact p,
end

theorem q06: ((P → Q) → P) → P :=
begin
  assume h,
  cases em P with p np,
  exact p,
  apply h,
  assume p,
  have f: false,
  apply np,
  exact p,
  cases f,
end 

theorem q07: ¬ ¬ (P ∨ ¬ P) → P ∨ ¬ P :=
begin
  assume h,
  cases em (P ∨ ¬ P) with h1 h2,
  exact h1,
  have f: false,
  apply h,
  exact h2,
  cases f,  
end

theorem q08: ¬ (P ∨ Q) ↔ ¬ ¬ ¬ (P ∨ Q) :=
begin
  constructor,
  assume h1,
  assume h2,
  apply h2,
  exact h1,
  assume h3,
  assume h4,
  apply h3,
  assume h5,
  apply h5,
  exact h4,
end
/- ---Do not add/change anything below this line --- -/
end proofs
