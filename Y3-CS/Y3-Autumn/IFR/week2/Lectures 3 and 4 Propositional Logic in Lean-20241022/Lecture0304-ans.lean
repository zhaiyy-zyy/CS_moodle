variables P Q R: Prop

example : P → Q → P ∧ Q :=
begin
  assume p,
  assume q,
  constructor,
  exact p,
  exact q,
end

theorem comAnd : P ∧ Q → Q ∧ P :=
begin
  assume pq,
  cases pq with p q,
  constructor,
  exact q,
  exact p,
end

theorem comAndIff : P ∧ Q ↔ Q ∧ P :=
begin
  constructor,
  apply comAnd,
  apply comAnd,
end

theorem curry : P ∧ Q → R ↔ P → Q → R :=
begin
  constructor,
  assume pqr,
  assume p,
  assume q,
  apply pqr,
  constructor,
  exact p,
  exact q,
  assume pqr,
  assume pq,
  cases pq with p q,
  apply pqr,
  exact p,
  exact q,
end

example : P → P ∨ Q :=
begin
    assume p,
    left,
    exact p,
end


theorem case_lem: (P → R) → (Q → R) → P ∨ Q → R :=
begin
    assume pr,
    assume qr,
    assume pq,
    cases pq with p q,
    apply pr,
    exact p,
    apply qr,
    exact q,
end

example : P ∧ (Q ∨ R) ↔ (P ∧ Q) ∨ (P ∧ R) :=
begin
  constructor,
  assume pqr,
  cases pqr with p qr,
  cases qr with q r,
  left,
  constructor,
  exact p,
  exact q,
  right,
  constructor,
  exact p,
  exact r,
  assume pqpr,
  cases pqpr with pq pr,
  cases pq with p q,
  constructor,
  exact p,
  left,
  exact q,
  cases pr with p r,
  constructor,
  exact p,
  right,
  exact r,
end

theorem case_thm: P ∨ Q → R ↔ (P → R) ∧ (Q → R) :=
begin
    constructor,
    assume pqr,
    constructor,
    assume p,
    apply pqr,
    left,
    exact p,
    assume q,
    apply pqr,
    right,
    exact q,
    assume prqr,
    assume pq,
    cases prqr with pr qr,
    cases pq with p q,
    apply pr,
    exact p,
    apply qr,
    exact q,
end

example : true :=
begin
  constructor,
end

theorem efq : false → P :=
begin
    assume f,
    cases f,
end

theorem contr: ¬ (P ∧ ¬ P) :=
begin
    assume pnp,
    cases pnp with p np,
    apply np,
    exact p,
end

variables P Q R : Prop


--variables P Q R : Prop

-- (P → Q) → R ↔ P → (Q → R)
example : ((P → Q) → R) ↔ (P → (Q → R)) :=
begin
  constructor,

  assume pqr,
  assume p,
  assume q,
  apply pqr,
  assume p,
  exact q,

  assume pqr,
  assume pq,
  apply h,
  assume p,
  apply h1,
  exact p,
end



















