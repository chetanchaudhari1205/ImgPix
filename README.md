# ImgPix

This repository hosts the ImgPix app that is used to retrieve and display the copyright free images
shared by the Pixabay users.

The following are few observations regarding the behaviour:

## Scenario 1:
The List Fragment was always recreating when coming back from the Detail Fragment.

### Therefore:
I had to remove the following code to navigate to the Detail Fragment:
val action = ListFragmentDirections.actionListFragmentToDetailFragment(hit)
view?.findNavController()?.navigate(action)

### Came across a known issue:
https://issuetracker.google.com/issues/109856764

## Scenario 2:
Used onCreateOptionsMenu() to create a menu in the ListFragment

### Problem is:
It is deprecated in java

### Solution for it is:

TESTED AND VERIFIED

To use the following code instead:
    
    fun getMenuProvider(): MenuProvider {
        return object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.search_bar -> {
                        val searchView = menuItem.actionView as SearchView
                        searchView.setOnQueryTextListener(getOnQueryTextListener())
                        true
                    }
                    else -> false
                }
            }
        }
    }