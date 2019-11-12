# Baking Time
Udacity Android Developer Nanodegree Project

Retrieves recipe information from the network as JSON and displays text and video steps.  Features support for different layout sizes.

## UI
### Recipe View
![Recipe View](https://github.com/joshua-hilborn/Baking/blob/master/img/Recipe%20View.png)
- Displays basic information about the recipe (ingredients list, RecyclerView of steps)
- Clicking on a step launches the step view for that step.
- Clicking "Send to Widget" will send the ingredients list to the widget.

### Step View
![Step View](https://github.com/joshua-hilborn/Baking/blob/master/img/Step%20View.png)
- Displays the title of the step, text instructions, and any related media (video or images)
- Clicking the arrows at the top will advance to the next/previous step.

### Widget
![Widget](https://github.com/joshua-hilborn/Baking/blob/master/img/Widget.png)
- Displays the steps for the selected recipe.

### Libraries and Services
- Gson 2.8.5
- Retrofit 2.5.0
- ExoPlayer 2.2.0
- Picasso 2.71828
