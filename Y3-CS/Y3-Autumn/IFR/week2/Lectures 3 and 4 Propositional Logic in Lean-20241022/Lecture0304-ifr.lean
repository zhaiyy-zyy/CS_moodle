variables P Q R: Prop

example : P → Q → P ∧ Q :=
begin
  sorry,
end

theorem comAnd : P ∧ Q → Q ∧ P :=
begin
  sorry,
end

theorem comAndIff : P ∧ Q ↔ Q ∧ P :=
begin
  sorry,
end

theorem curry : P ∧ Q → R ↔ P → Q → R :=
begin
  sorry,
end

example : P → P ∨ Q :=
begin
    sorry,
end


theorem case_lem: (P → R) → (Q → R) → P ∨ Q → R :=
begin
    sorry,
end

example : P ∧ (Q ∨ R) ↔ (P ∧ Q) ∨ (P ∧ R) :=
begin
  sorry,
end

theorem case_thm: P ∨ Q → R ↔ (P → R) ∧ (Q → R) :=
begin
    sorry,
end

example : true :=
begin
  sorry,
end

theorem efq : false → P :=
begin
    sorry,
end

theorem contr: ¬ (P ∧ ¬ P) :=
begin
    sorry,
end


















