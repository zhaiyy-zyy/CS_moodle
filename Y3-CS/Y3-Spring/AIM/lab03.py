import numpy as np

np.random.seed(0)

def target_function(x):
    """The target function we want to fit."""
    return 10 + 0.5 * (x - 5)**2

def function_to_fit(x, params):
    """The function we are trying to fit, parameterized by params = [a, b, c]."""
    a, b, c = params
    return a * x**2 + b * x + c

def cost_function(target_y, fitted_y):
    """Sum of squared errors between target and fitted function."""
    return np.sum((np.array(target_y) - np.array(fitted_y))**2)

def generate_neighbor_params(params, step_size=0.5):
    """Generates a slightly perturbed set of parameters."""
    return params + np.random.uniform(-step_size, step_size, size=len(params))

def simulated_annealing(initial_params, x_values, target_y_values, initial_temperature=100, cooling_rate=0.95, iterations=1000):
    """
    Performs simulated annealing to fit the function_to_fit to the target_function.

    Args:
        initial_params: Initial guess for parameters [a, b, c].
        x_values: x values for evaluation.
        target_y_values: y values of the target function.
        initial_temperature: Starting temperature for annealing.
        cooling_rate: Rate at which temperature decreases.
        iterations: Number of iterations for the annealing process.

    Returns:
        params_history: List of parameter sets at each iteration.
        cost_history: List of cost function values at each iteration.
    """
    current_params = np.array(initial_params)
    best_params = current_params.copy()
    current_cost = cost_function(target_y_values, function_to_fit(x_values, current_params))
    best_cost = current_cost
    params_history = [current_params.copy()]
    cost_history = [current_cost]
    temperature_history = [initial_temperature]

    temperature = initial_temperature

    for i in range(iterations):
        temperature *= cooling_rate
        temperature_history.append(temperature)

        neighbor_params = generate_neighbor_params(current_params)
        neighbor_cost = cost_function(target_y_values, function_to_fit(x_values, neighbor_params))

        cost_diff = neighbor_cost - current_cost

        if cost_diff < 0:
            current_params = neighbor_params
            current_cost = neighbor_cost
            if current_cost < best_cost:
                best_params = current_params.copy()
                best_cost = current_cost
        else:
            acceptance_probability = np.exp(-cost_diff / temperature)
            if np.random.rand() < acceptance_probability:
                current_params = neighbor_params
                current_cost = neighbor_cost

        params_history.append(current_params.copy())
        cost_history.append(current_cost)

    return params_history, cost_history, temperature_history, best_params, best_cost

# --- Visualization ---
x_values = np.linspace(-5, 15, 100)
target_y_values = target_function(x_values)

# Initial parameters and SA execution
initial_params = [1, 1, 1] # Initial guess for [a, b, c]
params_history, cost_history, temperature_history, best_params, best_cost = simulated_annealing(
    initial_params, x_values, target_y_values, 
    initial_temperature=1000, 
    cooling_rate=0.95, 
    
    iterations=500
)

print(f"Best parameters found: a={best_params[0]:.2f}, b={best_params[1]:.2f}, c={best_params[2]:.2f}")
print(f"Best cost: {best_cost:.2f}")
