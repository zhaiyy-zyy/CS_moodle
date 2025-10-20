variables A B: Type
variables PP QQ : A → Prop
variables P R : Prop



example : (∀ x: A, PP x ∧ QQ x)
        ↔ (∀ x: A, PP x) ∧ (∀ x: A, QQ x) :=
begin
    sorry,
end


example : (∀ x: A, PP x) 
    → (∀ y: A, PP y → QQ y) 
    → ∀ z : A, QQ z :=
begin
    sorry,
end

example : (∃ x : A, PP x)
        → (∀ y: A, PP y → QQ y)
        → (∃ z: A, QQ z) :=
begin
    sorry,
end

example : (∃ x: A, PP x ∨ QQ x)
        ↔ (∃ x: A, PP x) ∨ (∃ x: A, QQ x) :=
begin
    sorry,
end

theorem curry_pred: (∃ x: A, PP x) → R
                  ↔ (∀ x: A, PP x → R) :=
begin
     sorry,
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











