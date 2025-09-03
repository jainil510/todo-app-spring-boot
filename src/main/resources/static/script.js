document.addEventListener('DOMContentLoaded', () => {
    const todoInput = document.getElementById('todo-input');
    const addButton = document.getElementById('add-button');
    const todoList = document.getElementById('todo-list');

    const API_URL = '/api/todos';

    // Function to fetch all todos and display them
    const fetchTodos = async () => {
        try {
            const response = await fetch(API_URL);
            const todos = await response.json();
            todoList.innerHTML = ''; // Clear the list before adding new items
            todos.forEach(todo => {
                addTodoToList(todo);
            });
        } catch (error) {
            console.error('Error fetching todos:', error);
        }
    };

    // Function to add a single todo to the UI
    const addTodoToList = (todo) => {
        const li = document.createElement('li');
        li.dataset.id = todo.id;
        if (todo.completed) {
            li.classList.add('completed');
        }

        const span = document.createElement('span');
        span.textContent = todo.title;
        span.addEventListener('click', () => toggleComplete(todo.id, !todo.completed));

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'X';
        deleteButton.classList.add('delete-btn');
        deleteButton.addEventListener('click', (e) => {
            e.stopPropagation(); // Prevent the li click event from firing
            deleteTodo(todo.id);
        });

        li.appendChild(span);
        li.appendChild(deleteButton);
        todoList.appendChild(li);
    };

    // Function to add a new todo
    const addTodo = async () => {
        const title = todoInput.value.trim();
        if (title === '') return; // Don't add empty todos

        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ title, completed: false }),
            });
            const newTodo = await response.json();
            addTodoToList(newTodo);
            todoInput.value = ''; // Clear the input
        } catch (error) {
            console.error('Error adding todo:', error);
        }
    };

    // Function to toggle a todo's completed status
    const toggleComplete = async (id, completed) => {
        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                // We need to send the full object, even if we only change one field
                body: JSON.stringify({ 
                    title: document.querySelector(`li[data-id='${id}'] span`).textContent, 
                    completed 
                }),
            });
            const updatedTodo = await response.json();
            // Refresh the list to show the change
            fetchTodos(); 
        } catch (error) {
            console.error('Error updating todo:', error);
        }
    };

    // Function to delete a todo
    const deleteTodo = async (id) => {
        try {
            await fetch(`${API_URL}/${id}`, {
                method: 'DELETE',
            });
            // Refresh the list to show the change
            fetchTodos();
        } catch (error) {
            console.error('Error deleting todo:', error);
        }
    };

    // Add event listeners
    addButton.addEventListener('click', addTodo);
    todoInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            addTodo();
        }
    });

    // Initial fetch of todos when the page loads
    fetchTodos();
});
