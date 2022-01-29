# Page Fetch

**An app for demonstrating local and remote paging of data fetched from a remote datasource**

## Attribution

This app is built with Android Jetpack and Square Retrofit

## Prerequisites

1. Clone the repository by running [git clone https://github.com/rjbx/pagefetch.git]
2. Move the provided 'keys' resource file into the pagefetch repository path [app/src/main/res/values/]
3. In Android Studio, select a debug build variant from the BuildVariants tab or from the Build menu

## Description

**Flavors**

Nopage: Fetches and persists full list, then displays sorted unpaginated list data

Localpage:  
* Fetches and persists full list, then paginates and displays sorted list data
* Pagination size can be asynchronously updated by controller
* Updates are visible when page size is below RV page size
* Controller orientation can be updated by menu option
* ListView implementation omitted for brevity

Remotepage:
* Fetches and paginates sorted remote list data without local persistence
* Remote data can be paginated by item key, page key or positionally
* Pagination size can be asynchronously updated by controller
* Controller orientation can be updated by menu option
* Pagination type can be updated by menu option