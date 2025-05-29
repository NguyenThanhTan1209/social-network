package com.example.socialnetworkapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyScopeMarker
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.model.Post
import com.example.socialnetworkapp.model.User
import com.example.socialnetworkapp.state.UserState
import com.example.socialnetworkapp.viewmodel.ProfileViewModel
import com.example.socialnetworkapp.viewmodel.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val userState by viewModel.userInfo.collectAsState()
    val tabViewItems = listOf<TabViewItem>(
        TabViewItem(
            image = painterResource(R.drawable.grid_icon),
            text = "Posts"
        ),
        TabViewItem(
            image = painterResource(R.drawable.tags_icon),
            text = "Tags"
        ),
    )
    val mockPosts = listOf<Post>(
        Post(
            postId = "249-89-4363",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/500/500"
            )
        ),
        Post(
            postId = "972-97-5581",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/500/550"
            )
        ),
        Post(
            postId = "666-49-1808",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/500/600"
            )
        ),
        Post(
            postId = "926-00-1725",
            userId = "id376",
            imageUrls = listOf(
                "https://picsum.photos/500/650"
            )
        ),
        Post(
            postId = "249-89-4363",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/500/700"
            )
        ),
        Post(
            postId = "972-97-5581",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/500/750"
            )
        ),
        Post(
            postId = "666-49-1808",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/500/800"
            )
        ),
        Post(
            postId = "926-00-1725",
            userId = "id376",
            imageUrls = listOf(
                "https://picsum.photos/500/850"
            )
        ),
        Post(
            postId = "249-89-4363",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/500/900"
            )
        ),
        Post(
            postId = "972-97-5581",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/500/950"
            )
        ),
        Post(
            postId = "666-49-1808",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/550/500"
            )
        ),
        Post(
            postId = "926-00-1725",
            userId = "id376",
            imageUrls = listOf(
                "https://picsum.photos/600/500"
            )
        ),
        Post(
            postId = "249-89-4363",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/650/500"
            )
        ),
        Post(
            postId = "972-97-5581",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/700/500"
            )
        ),
        Post(
            postId = "666-49-1808",
            userId = "id375",
            imageUrls = listOf(
                "https://picsum.photos/750/500"
            )
        ),
        Post(
            postId = "926-00-1725",
            userId = "id376",
            imageUrls = listOf(
                "https://picsum.photos/800/600"
            )
        ),
    )

    // Check auth state on launch
    LaunchedEffect(key1 = true) {
        viewModel.getUserInfo()
    }

    when (userState) {
        is UserState.Init -> {

        }
        is UserState.Loading -> {
            CircularProgressIndicator()
        }
        is UserState.Error -> {
            Text(text = (userState as UserState.Error).message)  // Show error message
        }
        else -> {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                containerColor = MaterialTheme.colorScheme.secondary,
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        title = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text((userState as UserState.Success).user.userName)
                                }, colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            )
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Menu, contentDescription = "")
                            }
                        })
                },
            ) { it ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(bottom = it.calculateBottomPadding())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = (userState as UserState.Success).user.avatarUrl,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(86.dp)
                                .clip(CircleShape)
                        )
                        InforComponent(
                            info = "Posts",
                            data = (userState as UserState.Success).user.postCount
                        )
                        InforComponent(
                            info = "Followers",
                            data = (userState as UserState.Success).user.followerCount
                        )
                        InforComponent(
                            info = "Following",
                            data = (userState as UserState.Success).user.followingCount
                        )
                    }
                    Spacer(modifier = Modifier.height(17.dp))
                    BioComponent(
                        fullName = (userState as UserState.Success).user.fullName,
                        bio = (userState as UserState.Success).user.bio
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedButton(
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White,
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(
                            corner = CornerSize(6.dp)
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.15F)
                        ),
                        onClick = {},
                    ) {
                        Text(
                            "Edit Profile", style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(17.dp))
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.01f),
                        contentColor = Color.White,
                    ) {
                        tabViewItems.forEachIndexed { index, item ->
                            Tab(
                                selected = selectedTabIndex == index,
                                selectedContentColor = Color.White,
                                unselectedContentColor = Color.White.copy(alpha = 0.6f),
                                modifier = Modifier.height(44.dp),
                                onClick = {
                                    selectedTabIndex = index
                                }
                            ) {
                                Icon(
                                    item.image,
                                    contentDescription = item.text,
                                    modifier = Modifier.size(23.dp)
                                )
                            }
                        }
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                    ) {
                        items(mockPosts.size) { i ->
                            AsyncImage(
                                model = mockPosts[i].imageUrls[0],
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(RectangleShape)
                                    .aspectRatio(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoItem(post: Post) {
    AsyncImage(
        model = post.imageUrls[0],
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RectangleShape)
    )
}

@Composable
fun BioComponent(fullName: String, bio: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(fullName, style = MaterialTheme.typography.titleSmall)
        Text(bio, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun InforComponent(modifier: Modifier = Modifier, info: String, data: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("$data", style = MaterialTheme.typography.titleMedium)
        Text(info, style = MaterialTheme.typography.bodyMedium)
    }
}