variables A B: Type
variables PP QQ : A → Prop
variables P R : Prop



example : (∀ x: A, PP x ∧ QQ x)
        ↔ (∀ x: A, PP x) ∧ (∀ x: A, QQ x) :=
begin
    constructor,
    assume h1,
    constructor,
    assume a,
    have ha: PP a ∧ QQ a,
    apply h1,
    cases ha with ppa qqa,
    exact ppa,
    assume a,
    have ha: PP a ∧ QQ a,
    apply h1,
    cases ha with ppa qqa,
    exact qqa,
    assume h2,
    cases h2 with h3 h4,
    assume a,
    constructor,
    apply h3,
    apply h4,
end


example : (∀ x: A, PP x) 
    → (∀ y: A, PP y → QQ y) 
    → ∀ z : A, QQ z :=
begin
    assume h1,
    assume h2,
    assume z,
    apply h2,
    apply h1,
end

example : (∃ x : A, PP x)
        → (∀ y: A, PP y → QQ y)
        → (∃ z: A, QQ z) :=
begin
    assume h1,
    assume h2,
    cases h1 with a ha,
    existsi a,
    apply h2,
    exact ha,
end

example : (∃ x: A, PP x ∨ QQ x)
        ↔ (∃ x: A, PP x) ∨ (∃ x: A, QQ x) :=
begin
    constructor,
    assume h1,
    cases h1 with a ha,
    cases ha with ppa qqa,
    left,
    existsi a,
    exact ppa,
    right,
    existsi a,
    exact qqa,
    assume h2,
    cases h2 with h3 h4,
    cases h3 with a ppa,
    existsi a,
    left,
    exact ppa,
    cases h4 with a qqa,
    existsi a,
    right,
    exact qqa,
end

theorem curry_pred: (∃ x: A, PP x) → R
                  ↔ (∀ x: A, PP x → R) :=
begin
     constructor,
      assume h1,
      assume a,
      assume ppa,
      apply h1,
      existsi a,
      exact ppa,
      assume h2,
      assume h3,
      cases h3 with a ppa,
      have h: PP a → R,
      apply h2,
      apply h,
      exact ppa, 
end

example : ∀ x : A, x = x :=
begin
    sorry,
end

example : ∀ x y : A, x = y → PP y → PP x :=
begin
    sorry,
end

example : ∀ x y : A, x = y → PP x → PP y :=
begin
    sorry,
end

theorem sym_eq : ∀ x y : A, x = y → y = x :=
begin
    sorry,
end

theorem trans_eq: ∀ x y z : A, x = y → y = z → x = z :=
begin
   sorry,
end


theorem congr_argf: ∀ f : A → B, ∀ x y : A, x = y → f x = f y :=
begin
    sorry,
end


theorem dm1_pred : ¬ (∃ x : A, PP x) ↔ ∀ x : A, ¬ PP x :=
begin
    sorry,
end


theorem dm2_pred : ¬ (∀ x : A, PP x) ↔ ∃ x : A, ¬ PP x :=
begin
    sorry,
end











