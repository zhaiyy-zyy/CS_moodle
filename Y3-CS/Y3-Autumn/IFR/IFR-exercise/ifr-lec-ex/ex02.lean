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
  assume p pq,
  constructor,
  exact p,
  apply pq,
  exact p,
end

theorem q02: P ∨ Q → (P → Q) → Q :=
begin 
  assume porq,
  assume p2q,
  cases porq with p q,
  apply p2q,
  exact p,
  exact q,
end

open classical
theorem q03: (P → Q)→ ¬ P ∨ Q :=
begin
  assume pq,
  cases em P with p np,
  right,
  apply pq,
  exact p,
  left,
  exact np,
end

theorem q04: (¬ P ∨ Q) → P → Q :=
begin
  assume h,
  assume p,
  cases h with np q,
  have f : false,
  apply np,
  exact p,
  cases f,
  exact q,
end

theorem q05: ¬ P ↔ ¬ ¬ ¬ P :=
begin
  constructor,
  assume np,
  assume nnp,
  apply nnp,
  exact np,
  assume nnnp,
  assume p,
  apply nnnp,
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
  have f : false,
  apply np,
  exact p,
  cases f,
end 

theorem q07: ¬ ¬ (P ∨ ¬ P) → P ∨ ¬ P :=
begin
  assume h,
  cases em (P ∨ ¬ P) with h1 h2,
  exact h1,
  have f : false,
  apply h,
  exact h2,
  cases f,
end

theorem q08: ¬ (P ∨ Q) ↔ ¬ ¬ ¬ (P ∨ Q) :=
begin
  constructor,
  assume h,
  assume h1,
  apply h1,
  exact h,
  assume h2,
  assume h3,
  apply h2,
  assume h4,
  apply h4,
  exact h3,
end
/- ---Do not add/change anything below this line --- -/
end proofs
