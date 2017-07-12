# Basic Calls
Hi everyone, here are some example calls for Medal. All response are given in JSON only. We will add more functionality soon (such as being able to upvote inside discord, submitting clips, and seeing results from a specific discord channel. For now you'll have to use MedalBot for these features.)


## Sync your game database with ours


Get started by syncing your local games with ours. You can do this by syncing with our categories endpoint.

For example: Do a request to https://api.gomedal.com/categories and you'll receive a JSON array of categories like this:

```json
[{"categoryId":39,"categoryName":"Battlefield","categoryThumbnail":"https://static-cdn.jtvnw.net/ttv-boxart/Battlefield%201-52x72.jpg","categoryBackground":"https://static-cdn.jtvnw.net/ttv-boxart/Battlefield%201-52x72.jpg","categoryFollowers":52,"categoryPublishers":1,"isFeatured":1},{"categoryId":41,"categoryName":"PUBG","categoryThumbnail":"https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-272x380.jpg","categoryBackground":"https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-272x380.jpg","categoryFollowers":104,"categoryPublishers":1,"isFeatured":1}]
```

Simple save the categoryName and the categoryId to a local array and save it. You'll need it later.


## Now you can make API calls

After you've synced the games, you can request trending clips. This is how it works:

Send a GET request to 

```
https://api.gomedal.com/botclips
```

### Here are some examples:

Return 1 random clip (useful for .randomclip command):
```
https://api.gomedal.com/botclips?random=true&limit=1
```

Return 1 random PUBG clip (useful for .randomclip command):
```
https://api.gomedal.com/botclips?random=true&categoryId=41
```

Return the top 10 PUBG clips of that day:
```
https://api.gomedal.com/botclips?categoryId=41&limit=10
```

Note here that 41 is the categoryId that you also used when you synced your games with ours. This way, if a user types ".topclips PUBG" you can check which categoryName matches with the user input (and maybe write some exceptions) and assign the right categoryId.

Return the global top 50 of that day
```
https://api.gomedal.com/botclips?limit=50
```

It returns a JSON array with content objects

```json
[ { contentId: 493654,
    contentType: 15,
    categoryId: 41,
    contentTitle: 'Josh_OG Shooting threw smoke? Explain?',
    contentUrl: 'https://gomedal.com/clips/493654',
    thumbnail1080p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-k0lezoeret3kyjjr-1080p.jpg',
    thumbnail720p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-k0lezoeret3kyjjr-720p.jpg',
    thumbnail480p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-k0lezoeret3kyjjr-480p.jpg',
    thumbnail360p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-k0lezoeret3kyjjr-360p.jpg',
    thumbnail240p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-k0lezoeret3kyjjr-240p.jpg',
    thumbnail144p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-k0lezoeret3kyjjr-144p.jpg',
    videoLengthSeconds: 31,
    likes: 1,
    views: 9,
    thumbnailUrl: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-k0lezoeret3kyjjr-1080p.jpg',
    credits: 'Medal Discord,https://discord.gg/KEuwx6s,Discord' },
  { contentId: 493659,
    contentType: 15,
    categoryId: 41,
    contentTitle: 'Can I flip your car?',
    contentUrl: 'https://gomedal.com/clips/493659',
    thumbnail1080p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-s1a8urla2izl88q3-1080p.jpg',
    thumbnail720p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-s1a8urla2izl88q3-720p.jpg',
    thumbnail480p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-s1a8urla2izl88q3-480p.jpg',
    thumbnail360p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-s1a8urla2izl88q3-360p.jpg',
    thumbnail240p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-s1a8urla2izl88q3-240p.jpg',
    thumbnail144p: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-s1a8urla2izl88q3-144p.jpg',
    videoLengthSeconds: 5,
    likes: 1,
    views: 4,
    thumbnailUrl: 'https://s3.amazonaws.com/gomedal2/img/thumbnail-s1a8urla2izl88q3-1080p.jpg',
    credits: 'Medal Discord,https://discord.gg/KEuwx6s,Discord' } ]
```


This should be everything you need to implement .randomclip, .randomclip {GAME_NAME}, .topclips and .topclips {GAME_NAME}.


