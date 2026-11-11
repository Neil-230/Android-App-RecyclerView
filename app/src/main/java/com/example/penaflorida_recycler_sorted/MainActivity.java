package com.example.penaflorida_recycler_sorted;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private List<Person> originalData;
    private List<Person> displayedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        originalData = Arrays.asList(
            new Person("John", "Male"),
            new Person("Alice", "Female"),
            new Person("Michael", "Male"),
            new Person("Sophia", "Female"),
            new Person("David", "Male"),
            new Person("Emma", "Female"),
            new Person("James", "Male"),
            new Person("Olivia", "Female"),
            new Person("Robert", "Male"),
            new Person("Isabella", "Female")
        );

        displayedData = new ArrayList<>(originalData);

        personAdapter = new PersonAdapter(displayedData);
        recyclerView.setAdapter(personAdapter);

        Button sortButton = findViewById(R.id.sort_button);
        sortButton.setOnClickListener(v -> sortAlphabetically());

        Button filterMaleButton = findViewById(R.id.filter_male_button);
        filterMaleButton.setOnClickListener(v -> filterByGender("Male"));

        Button filterFemaleButton = findViewById(R.id.filter_female_button);
        filterFemaleButton.setOnClickListener(v -> filterByGender("Female"));

        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> resetList());

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterByName(newText);
                return false;
            }
        });
    }

    private void sortAlphabetically() {
        Collections.sort(displayedData, Comparator.comparing(Person::getName));
        personAdapter.setData(displayedData);
    }

    private void filterByGender(String gender) {
        displayedData = originalData.stream()
                .filter(person -> person.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
        personAdapter.setData(displayedData);
    }

    private void resetList() {
        displayedData = new ArrayList<>(originalData);
        personAdapter.setData(displayedData);
    }

    private void filterByName(String name) {
        if (name == null || name.isEmpty()) {
            resetList();
            return;
        }

        displayedData = originalData.stream()
                .filter(person -> person.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        personAdapter.setData(displayedData);
    }
}
