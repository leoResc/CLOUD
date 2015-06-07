require "seleniumUtilities"

class Song < SeleniumUtilities
	
	@songname
	
	def initialize(driver)
		@driver = driver
	end
	
	def uploadSong(song)
		# selenium cannot handle the dropzone -> manually select file
	end
	
	def buttonEnabled()
		(@driver.find_element(:id, "upload").attribute("disabled")).should == nil
	end
	
	def waitForUpload()
		# no upload possible as dropzone is not accessible by selenium webdriver
		#while @driver.find_element(:id, "progress").text != "100 %" do
		#	sleep(1)
		#end
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
