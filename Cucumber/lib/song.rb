require "utilities"

class Song < Utilities
	
	@songname
	
	def initialize(driver)
		@driver = driver
	end
	
	def uploadSong(song)
		# selenium cannot handle the dropzone -> manually select file
		@songname = song
		sleep(15)
		filename = './features/TestSong.mp3'
		file = File.join(Dir.pwd, filename)
	end
	
	def buttonEnabled()
		(@driver.find_element(:id, "upload").attribute("disabled")).should == nil
	end
	
	def waitForUpload()
		while @driver.find_element(:id, "progress").text != "100 %" do
			sleep(1)
		end
		sleep(3)
	end
	
	def assertSongIsUploaded()
		index = 1
		success = false
		while !success
			if @driver.find_element(:xpath, "//ul[@id='allSongs']/li[#{index}]").text == @songname
				success = true
			end
			index += 1
		end
  	end
	
end
