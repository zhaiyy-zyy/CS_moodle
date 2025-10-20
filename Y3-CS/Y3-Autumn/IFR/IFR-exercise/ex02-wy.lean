/-
COMP2068 IFR 
Exercise 02

Prove all the following propositions in Lean.
This is to replace "sorry" with your proof. 

You are only allowed to use the tactics introduced in the lecture.
You may use laws in classical logic if a statement is not provable in intuitionistic logic. 
-/

open classical

namespace proofs

variables P Q R : Prop

/- ---Do not add/change anything above this line --- -/

theorem q01: P → (P → Q) → P ∧ Q :=
begin
  assume p,
  assume ptoq,
  constructor,
  exact p,
  apply ptoq,
  exact p,
end

theorem q02: P ∨ Q → (P → Q) → Q :=
begin 
  assume porq,
  cases porq with p q,
  assume ptoq,
  apply ptoq,
  exact p,
  assume ptoq,
  exact q,
end


theorem q03: (P → Q)→ ¬ P ∨ Q :=
begin
  assume ptoq,
  cases em P with p notp, 
  right,
  apply ptoq,
  exact p,
  left,
  exact notp,
end

theorem q04: (¬ P ∨ Q) → P → Q :=
begin
  assume notporq,
  assume p,
  cases notporq with notp q,
  have f:false,
  apply notp,
  exact p,
  cases f,
  exact q,
end

theorem q05: ¬ P ↔ ¬ ¬ ¬ P :=
begin
  constructor,
  assume notp,
  assume notnotp,
  cases em P with p notp,
  apply notp,
  exact p,
  apply notnotp,
  exact notp,
  assume notnotnotp,
  assume p,
  apply notnotnotp,
  assume notp,
  apply notp,
  exact p,
end


theorem q06: ((P → Q) → P) → P :=
begin
  assume ptoqtop,
  cases em P with  p notp,
  exact p,
  have f:false,
  apply notp,
  apply ptoqtop,
  assume p,
  cases em Q with q notq,
  exact q,
  have f2:false,
  apply notp,
  exact p,
  cases f2,
  cases f,
end 

theorem q07: ¬ ¬ (P ∨ ¬ P) → P ∨ ¬ P :=
begin
  assume notnotpornotp,
  cases em P with p notp,
  left,
  exact p,
  right,
  exact notp,
end

theorem q08: ¬ (P ∨ Q) ↔ ¬ ¬ ¬ (P ∨ Q) :=
begin
  constructor,
  assume notporq,
  assume notnotporq,
  apply notnotporq,
  exact notporq,
  assume notnotnotporq,
  assume porq,
  apply notnotnotporq,
  assume notporq,
  apply notporq,
  exact porq,
end
/- ---Do not add/change anything below this line --- -/
end proofs
