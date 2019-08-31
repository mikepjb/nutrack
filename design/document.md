# Design Document

Nutrack is a product intended commercially to:
  - showcase my ability as a software engineer.
  - whitelabel and provide it's functionality.

### What is Nutrack?

Nutrack is a platform that helps you track & plan what you consume. It helps
you to look for food that fits your needs and combine them into recipes that
you can view as a shopping list, making it easier to eat well and know how much
you are paying for it.

### Breakdown (draft 1)

- food searching view, user types into search bar and gets back food/recipe matches
- shopping list view (maybe combined, user searches and adds to list?)

+ for now - let's keep this as one view as see how far it goes, roughly:

[Logo]

[Search Bar]

[Info Box for selected result]

[Shopping List/Basket (with quantities, maybe switchable views e.g 'recipe mode')]

[Total]

### How?

Tesco provide an API to access their product information.. we can also find
some food information to load into PostgreSQL.. that is as far as I've thought
so far.

# References

UK Government has nutrient content data.
https://www.gov.uk/government/publications/composition-of-foods-integrated-dataset-cofid

Technical Difficulties accessing Clojure running on host from docker-compose (in Linux):
https://stackoverflow.com/questions/31324981/how-to-access-host-port-from-docker-container

## Commercial Viability

chompthis.com is a company that provide a Food Database API
