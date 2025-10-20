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
  sorry,
end

theorem q02: P ∨ Q → (P → Q) → Q :=
begin 
  sorry,
end


theorem q03: (P → Q)→ ¬ P ∨ Q :=
begin
  sorry,
end

theorem q04: (¬ P ∨ Q) → P → Q :=
begin
  sorry,
end

theorem q05: ¬ P ↔ ¬ ¬ ¬ P :=
begin
  sorry,
end


theorem q06: ((P → Q) → P) → P :=
begin
  sorry,
end 

theorem q07: ¬ ¬ (P ∨ ¬ P) → P ∨ ¬ P :=
begin
  sorry,
end

theorem q08: ¬ (P ∨ Q) ↔ ¬ ¬ ¬ (P ∨ Q) :=
begin
  sorry,
end
/- ---Do not add/change anything below this line --- -/
end proofs
